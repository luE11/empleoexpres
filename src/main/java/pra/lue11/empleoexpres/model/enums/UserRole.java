package pra.lue11.empleoexpres.model.enums;

/**
 * @author luE11 on 17/07/23
 */
public enum UserRole {
    ADMIN("ADMIN"), CANDIDATE("CANDIDATE"), PUBLISHER("PUBLISHER");

    final String roleName;

    UserRole(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }
}
