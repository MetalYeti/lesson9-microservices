package com.geekbrains.filestore.repositories.interfaces;


import com.geekbrains.filestore.entities.FileMetaDTO;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface IFileMetaProvider {

    String checkFileExists(UUID fileHash, String filename);

    List<String> checkFilesExistsByHash(UUID fileHash);

    /**
     * Сохранить метаданные файла
     *
     */
    void saveFileMeta(UUID Hash, String fileName, int sybType);

    Collection<FileMetaDTO> getMetaFiles(int subtype);

    void deleteMetaFile(UUID fileHash, String fileName);
}
