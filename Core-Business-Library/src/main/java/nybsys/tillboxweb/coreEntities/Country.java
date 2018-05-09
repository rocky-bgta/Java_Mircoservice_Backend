package nybsys.tillboxweb.coreEntities;
import nybsys.tillboxweb.BaseEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
public class Country extends BaseEntity {

    @Id
    @GeneratedValue(generator = "IdGen")
    @GenericGenerator(name = "IdGen", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters =
                    {
                            @org.hibernate.annotations.Parameter(name = "sequence_name", value = "countryID_seq"),
                            @org.hibernate.annotations.Parameter(name = "optimizer", value = "pooled"),
                            @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                            @org.hibernate.annotations.Parameter(name = "increment_size", value = "1"),
                    })
    private Integer countryID;

    @Column
    @NotNull(message = "Country Code cannot be empty")
    private String countryCode;

    @Column
    @NotNull(message = "Name cannot be empty")
    private String name;


    public Integer getCountryID() {
        return countryID;
    }

    public void setCountryID(Integer countryID) {
        this.countryID = countryID;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Country)) return false;
        if (!super.equals(o)) return false;
        Country country = (Country) o;
        return Objects.equals(getCountryID(), country.getCountryID()) &&
                Objects.equals(getCountryCode(), country.getCountryCode()) &&
                Objects.equals(getName(), country.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getCountryID(), getCountryCode(), getName());
    }


    @Override
    public String toString() {
        return "Country{" +
                "countryID=" + countryID +
                ", countryCode='" + countryCode + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
