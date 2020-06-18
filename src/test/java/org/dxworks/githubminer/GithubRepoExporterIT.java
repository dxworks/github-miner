package org.dxworks.githubminer;

import org.dxworks.githubminer.dto.export.RemoteInfoDTO;
import org.dxworks.githubminer.utils.TestUtils;
import org.dxworks.utils.java.rest.client.utils.JsonMapper;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class GithubRepoExporterIT {
	private final GithubRepoExporter githubRepoExporter = new GithubRepoExporter("andrei2699", "Music-Events-Application", TestUtils.getGithubCredentials());

	@Test
	void export() throws IOException {
		RemoteInfoDTO export = githubRepoExporter.export();
		assertNotNull(export);


		File file = Paths.get("../remote-export.json").toFile();
		new JsonMapper().writePrettyJSONtoFile(file, export);
	}
}