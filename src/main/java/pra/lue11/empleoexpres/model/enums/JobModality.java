package pra.lue11.empleoexpres.model.enums;

/**
 * @author luE11 on 17/07/23
 */
public enum JobModality {
    ALL("Todos"), FACETOFACE("Presencial"), REMOTE("Remoto");

    final String modality;

    JobModality(String modality) {
        this.modality = modality;
    }

    public String getModality() {
        return modality;
    }
}
