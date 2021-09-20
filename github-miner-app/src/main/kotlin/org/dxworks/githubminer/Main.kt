package org.dxworks.githubminer

import com.google.api.client.http.factory.CachingGithubHttpClientFactory
import org.dizitart.no2.Nitrite
import org.dxworks.argumenthor.Argumenthor
import org.dxworks.argumenthor.config.ArgumenthorConfiguration
import org.dxworks.argumenthor.config.fields.impl.StringField
import org.dxworks.argumenthor.config.fields.impl.StringListField
import org.dxworks.argumenthor.config.sources.impl.ArgsSource
import org.dxworks.argumenthor.config.sources.impl.EnvSource
import org.dxworks.argumenthor.config.sources.impl.PropertiesSource
import org.dxworks.githubminer.config.BooleanField
import org.dxworks.githubminer.config.DateField
import org.dxworks.githubminer.config.Repo
import org.dxworks.githubminer.config.RepoListField
import org.dxworks.githubminer.constants.ANONYMOUS
import org.dxworks.githubminer.constants.GITHUB_API_PATH
import org.dxworks.githubminer.http.factory.DefaultGithubHttpClientFactory
import org.dxworks.utils.java.rest.client.utils.JsonMapper
import java.nio.file.Files
import java.nio.file.Paths
import java.time.LocalDate


private const val GITHUB_REPOS = "github.repos"
private const val SINCE = "since"
private const val GITHUB_TOKENS = "github.tokens"
private const val GITHUB_BASE_PATH = "github.base.path"
private const val CACHE = "cache"

private const val RESULTS_FOLDER = "results"
private const val CACHE_FOLDER = "cache"

fun main(args: Array<String>) {
    val argumenthor = Argumenthor(ArgumenthorConfiguration(
        listOf(
            StringField(GITHUB_BASE_PATH, GITHUB_API_PATH),
            RepoListField(GITHUB_REPOS, emptyList()),
            StringListField(GITHUB_TOKENS, listOf(ANONYMOUS)),
            BooleanField(CACHE, true),
            DateField(SINCE)
        )
    ).apply {
        addSource(ArgsSource().also { it.argsList = args.toList() })
        addSource(PropertiesSource().also { it.path = "config/github-miner.properties" })
        addSource(EnvSource())
    })

    val githubBasePath: String = argumenthor.getValue(GITHUB_BASE_PATH)!!
    val repos: List<Repo> = argumenthor.getValue(GITHUB_REPOS)!!
    val tokens: List<String> = argumenthor.getValue(GITHUB_TOKENS)!!
    val since: LocalDate? = argumenthor.getValue(SINCE)

    val resultsPath = Paths.get(RESULTS_FOLDER)
    if (!Files.exists(resultsPath))
        resultsPath.toFile().mkdirs()


    val (clientFactory, database) = if (argumenthor.getValue<Boolean>(CACHE)!!) {
        val database: Nitrite = prepareCache()
        Pair(CachingGithubHttpClientFactory(database.getRepository(GithubResponseCache::class.java)), database)
    } else Pair(DefaultGithubHttpClientFactory(), null)

    try {
        repos.forEach { repo ->
            val export = GithubRepoExporter(
                repo.user,
                repo.repo,
                githubBasePath,
                tokens,
                clientFactory,
                since
            ).export()
            JsonMapper().writeJSONtoFile(
                Paths.get(RESULTS_FOLDER, "${repo.user}-${repo.repo}-prs.json").toFile(),
                export
            )
        }
    } catch (e: Exception) {
        e.printStackTrace()
    } finally {
        database?.close()
    }
}

private fun prepareCache(): Nitrite {
    val cachePath = Paths.get(CACHE_FOLDER)
    if (!Files.exists(cachePath))
        cachePath.toFile().mkdirs()
    return Nitrite.builder()
        .filePath(cachePath.resolve("github-miner.db").toFile())
        .openOrCreate("test", "test")
}
