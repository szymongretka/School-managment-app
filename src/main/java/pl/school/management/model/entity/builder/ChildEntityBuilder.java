package pl.school.management.model.entity.builder;

import pl.school.management.model.entity.Child;
import pl.school.management.model.entity.Parent;
import pl.school.management.model.entity.School;

public class ChildEntityBuilder {

    private Long id;
    private String firstName;
    private String lastName;
    private Parent parent;
    private School school;

    public ChildEntityBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public ChildEntityBuilder withFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public ChildEntityBuilder withLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public ChildEntityBuilder withParent(Parent parent) {
        this.parent = parent;
        return this;
    }

    public ChildEntityBuilder withSchool(School school) {
        this.school = school;
        return this;
    }

    public Child build() {
        Child child = new Child();
        child.setFirstName(firstName);
        child.setLastName(lastName);
        child.setParent(parent);
        child.setSchool(school);
        return child;
    }

}
