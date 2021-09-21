package org.dxworks.githubminer.config

data class Repo(val user: String, val repo: String) {
    override fun toString(): String {
        return "$user/$repo"
    }
}
