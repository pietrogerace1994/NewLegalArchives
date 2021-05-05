package com.ibm.la.mongo.interfaces;

import java.util.HashMap;
import java.util.Map;

public class DdsDocumentPermissionsTOREMOVE {
    private final Map<String, DdsPermissionTOREMOVE> userPermission = new HashMap<>();

    public void addPermission(final String userId, final DdsPermissionTOREMOVE permission) {
        userPermission.put(userId, permission);
    }

    public void removePermission(final String userId) {
        userPermission.remove(userId);
    }

    public Map<String, DdsPermissionTOREMOVE> getUserPermission() {
        return userPermission;
    }

}
