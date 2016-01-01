package com.anton.project.backup.do_export;

public class ExportProviderFactory {
	public static ExportProvider getProvider(ExportFileType type) {
		switch (type) {
			case XML: {
				return new XmlExportProvider();
			}
			default: return null;
		}
	}
}
