package com.nami.sonar.test;

import static com.nami.sonar.rule.compat.CompatibilityHelper.wrap;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.function.Consumer;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.sonar.api.batch.fs.internal.DefaultFileSystem;
import org.sonar.api.batch.fs.internal.DefaultIndexedFile;
import org.sonar.api.batch.fs.internal.DefaultInputFile;
import com.nami.sonar.rule.checks.SqlCheckRule;
import com.nami.sonar.rule.checks.XmlFile;
import com.nami.sonar.rule.checks.XmlSourceCode;
import com.nami.sonar.rule.compat.CompatibleInputFile;

public class SqlCheckRuleTest {

	@org.junit.Rule
	public TemporaryFolder temporaryFolder = new TemporaryFolder();
	
	static final File PATH = new File("src/test/resources/checks/generic");

	@Test
	public void violateSqlCheck() throws IOException {
		if (PATH.exists() && PATH.isDirectory()) {
			File[] files = PATH.listFiles((f) -> f.getName().endsWith("xml"));
			SqlCheckRule check = new SqlCheckRule();
			for (File file : files) {
				CompatibleInputFile newInputFile = newInputFile(file);
				XmlSourceCode xmlSourceCode = new XmlSourceCode(new XmlFile(newInputFile));
				/*if (xmlSourceCode.parseSource()) {
					check.validate(xmlSourceCode);
				}*/
				check.validate(xmlSourceCode);
			}
		}
	}
	
	protected DefaultFileSystem createFileSystem() throws IOException {
	    File workDir = temporaryFolder.newFolder("temp");
	    DefaultFileSystem fs = new DefaultFileSystem(workDir);
	    fs.setEncoding(Charset.defaultCharset());
	    fs.setWorkDir(workDir);

	    return fs;
	  }
	
	private CompatibleInputFile newInputFile(File file) {
		DefaultIndexedFile indexedFile = new DefaultIndexedFile("modulekey", file.getParentFile().toPath(), file.getName());
		Consumer<DefaultInputFile> metadataGenerator = (t)->{};
		DefaultInputFile defaultInputFile = new DefaultInputFile(indexedFile, metadataGenerator);
		defaultInputFile.setCharset(StandardCharsets.UTF_8);
	    return wrap(defaultInputFile);
	  }
}
