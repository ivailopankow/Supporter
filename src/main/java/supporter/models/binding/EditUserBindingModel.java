package supporter.models.binding;

import supporter.models.Role;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ivaylo on 25-Nov-16.
 */
public class EditUserBindingModel extends UserBindingModel{
    private List<Integer> roles;

    public EditUserBindingModel() {
        this.roles = new ArrayList<>();
    }

    public List<Integer> getRoles() {
        return roles;
    }

    public void setRoles(List<Integer> roles) {
        this.roles = roles;
    }
}
