package com.anton.project.backup.do_import;

import java.io.InputStream;
import java.util.List;

import com.anton.project.domain.Folder;

public class ImportManager {
	public static List<Folder> doImport(InputStream inputStream) {
		ImportProvider provider = new XmlImportProvider();
		
		return provider.doImport(inputStream);
	}
}
