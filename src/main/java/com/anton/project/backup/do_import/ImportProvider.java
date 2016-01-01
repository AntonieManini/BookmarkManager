package com.anton.project.backup.do_import;

import java.io.InputStream;
import java.util.List;

import com.anton.project.domain.Folder;

public interface ImportProvider {
	List<Folder> doImport(InputStream in);
}
