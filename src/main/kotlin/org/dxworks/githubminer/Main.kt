package org.dxworks.githubminer

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
import org.dxworks.utils.java.rest.client.utils.JsonMapper
import java.nio.file.Files
import java.nio.file.Paths

private const val GITHUB_REPOS = "github.repos"
private const val GITHUB_TOKENS = "github.tokens"
private const val GITHUB_BASE_PATH = "github.base.path"

private const val RESULTS_FOLDER = "results"

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

    repos.forEach { repo ->
        val export = GithubRepoExporter(repo.user, repo.repo, githubBasePath, tokens).export()
        JsonMapper().writeJSONtoFile(Paths.get(RESULTS_FOLDER, "${repo.user}-${repo.repo}-prs.json").toFile(), export)
    }
}
