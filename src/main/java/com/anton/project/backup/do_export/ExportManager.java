package com.anton.project.backup.do_export;

import java.io.PrintWriter;
import java.util.List;

import com.anton.project.domain.Folder;

public class ExportManager {
	public static void doExport(List<Folder> folders, String fileType, PrintWriter writer) {
		ExportFileType type = null;
		
		switch (fileType) {
			case "txt": {type = ExportFileType.TXT; break;}
			case "xml": {type = ExportFileType.XML; break;}
			case "html": {type = ExportFileType.HTML; break;}
		}
		
		ExportProvider provider = ExportProviderFactory.getProvider(type);
		provider.doExport(folders, writer);
	}
}
