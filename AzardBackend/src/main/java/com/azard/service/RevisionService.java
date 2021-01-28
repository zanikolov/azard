package com.azard.service;

import java.sql.SQLException;
import java.util.List;

import com.azard.model.Revision;
import com.azard.model.RevisionItem;
import com.azard.model.RevisionType;

public interface RevisionService {

	Revision initiateRevision(Revision revision) throws SQLException;

	Revision getRevision(Integer revisionId);

	Revision getCurrentRevision(Integer storeId);

	List<RevisionType> getRevisionTypes();

	RevisionItem getRevisionItemByBarcode(Integer revisionId, String barcode);

	Integer findRevisionItem(RevisionItem revisionItem) throws SQLException;

	Revision submitRevision(Revision revision);

	List<Revision> searchRevisions(Long startDateMilliseconds, Long endDateMilliseconds, Integer storeId);

	Integer correctionOfItemQuantityAfterRevision(RevisionItem revisionItem);

}
