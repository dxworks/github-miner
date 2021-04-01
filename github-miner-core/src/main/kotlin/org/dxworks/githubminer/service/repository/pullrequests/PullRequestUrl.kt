package org.dxworks.githubminer.service.repository.pullrequests

import com.google.api.client.http.GenericUrl
import com.google.api.client.util.Key

class PullRequestUrl(encodedUrl: String?, @Key val state: String) : GenericUrl(encodedUrl)
