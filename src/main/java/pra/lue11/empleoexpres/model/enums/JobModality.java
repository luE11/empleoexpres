package pra.lue11.empleoexpres.model.enums;

/**
 * @author luE11 on 17/07/23
 */
public enum JobModality {
    ALL("ALL"), FACETOFACE("FACETOFACE"), REMOTE("REMOTE");

    final String modality;

    JobModality(String modality) {
        this.modality = modality;
    }

    public String getModality() {
        return modality;
    }
}
