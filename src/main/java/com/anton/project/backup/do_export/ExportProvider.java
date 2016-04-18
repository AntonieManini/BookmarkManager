package com.anton.project.backup.do_export;

import java.io.PrintWriter;
import java.util.List;

import com.anton.project.domain.Folder;

public interface ExportProvider {
	void doExport(List<Folder> folders, PrintWriter writer);
}
