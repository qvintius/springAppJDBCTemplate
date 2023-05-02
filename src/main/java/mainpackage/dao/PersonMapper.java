package mainpackage.dao;

import mainpackage.models.Person;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonMapper implements RowMapper<Person> {
    @Override
    public Person mapRow(ResultSet rs, int rowNum) throws SQLException {
        Person person = new Person(rs.getInt("id_person"), rs.getString("name_person"),
                rs.getInt("age"), rs.getString("email"), rs.getString("address"));
        return person;
    }
}
