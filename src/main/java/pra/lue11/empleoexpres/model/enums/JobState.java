package pra.lue11.empleoexpres.model.enums;

/**
 * @author luE11 on 16/08/23
 */
public enum JobState {
    ACTIVE("Activo"), CLOSED("Finalizado");

    final String state;

    JobState(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }
}
