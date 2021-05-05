package com.ibm.la.mongo.interfaces;

public class DdsPermissionTOREMOVE {

    public static final DdsPermissionTOREMOVE DEFAULT = new DdsPermissionTOREMOVE();

    public static final DdsPermissionTOREMOVE READER = new DdsPermissionTOREMOVE(true, false, false, false);
    public static final DdsPermissionTOREMOVE WRITER = new DdsPermissionTOREMOVE(true, true, false, false);
    public static final DdsPermissionTOREMOVE ADMIN = new DdsPermissionTOREMOVE(true, true, false, false);

    private final Boolean r;
    private final Boolean w;
    private final Boolean d;
    private final Boolean p;

    public DdsPermissionTOREMOVE() {
        r = false;
        w = false;
        d = false;
        p = false;
    }

    public DdsPermissionTOREMOVE(final Boolean r, final Boolean w, final Boolean d, final Boolean p) {
        this.r = r;
        this.w = w;
        this.d = d;
        this.p = p;
    }

    public Boolean getD() {
        return d;
    }

    public Boolean getP() {
        return p;
    }

    public Boolean getR() {
        return r;
    }

    public Boolean getW() {
        return w;
    }
}

