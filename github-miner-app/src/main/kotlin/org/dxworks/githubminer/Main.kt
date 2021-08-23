package org.dxworks.githubminer

import org.dizitart.no2.Nitrite
import org.dxworks.argumenthor.Argumenthor
import org.dxworks.argumenthor.config.ArgumenthorConfiguration
import org.dxworks.argumenthor.config.fields.impl.StringField
import org.dxworks.argumenthor.config.fields.impl.StringListField
import org.dxworks.argumenthor.config.sources.impl.ArgsSource
import org.dxworks.argumenthor.config.sources.impl.EnvSource
import org.dxworks.argumenthor.config.sources.impl.PropertiesSource
import org.dxworks.githubminer.config.Repo
import org.dxworks.githubminer.config.RepoListField
import org.dxworks.githubminer.constants.ANONYMOUS
import org.dxworks.githubminer.constants.GITHUB_API_PATH
import org.dxworks.githubminer.dto.commons.User
import org.dxworks.utils.java.rest.client.utils.JsonMapper
import java.nio.file.Files
import java.nio.file.Paths


private const val GITHUB_REPOS = "github.repos"
private const val GITHUB_TOKENS = "github.tokens"
private const val GITHUB_BASE_PATH = "github.base.path"

private const val RESULTS_FOLDER = "results"
private const val CACHE_FOLDER = "cache"

fun main(args: Array<String>) {
    val argumenthor = Argumenthor(ArgumenthorConfiguration(
        listOf(
            StringField(GITHUB_BASE_PATH, GITHUB_API_PATH),
            RepoListField(GITHUB_REPOS, emptyList()),
            StringListField(GITHUB_TOKENS, listOf(ANONYMOUS))
        )
    ).apply {
        addSource(ArgsSource().also { it.argsList = args.toList() })
        addSource(PropertiesSource().also { it.path = "config/github-miner.properties" })
        addSource(EnvSource())
    })

    val githubBasePath: String = argumenthor.getValue(GITHUB_BASE_PATH)!!
    val repos: List<Repo> = argumenthor.getValue(GITHUB_REPOS)!!
    val tokens: List<String> = argumenthor.getValue(GITHUB_TOKENS)!!

    val resultsPath = Paths.get(RESULTS_FOLDER)
    if(!Files.exists(resultsPath))
        resultsPath.toFile().mkdirs()

    val cachePath = Paths.get(CACHE_FOLDER)
    if(!Files.exists(cachePath))
        cachePath.toFile().mkdirs()

    val database: Nitrite = Nitrite.builder()
        .filePath(cachePath.resolve("github-miner.db").toFile())
        .openOrCreate("test", "test")

    val cacheProcessor = CachingProcessor(database.getRepository(GithubResponseCache::class.java))

    repos.forEach { repo ->
        val export = GithubRepoExporter(repo.user, repo.repo, githubBasePath, tokens, cacheProcessor).export()
        JsonMapper().writeJSONtoFile(Paths.get(RESULTS_FOLDER, "${repo.user}-${repo.repo}-prs.json").toFile(), export)
    }
}
