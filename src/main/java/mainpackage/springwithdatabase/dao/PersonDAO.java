package mainpackage.springwithdatabase.dao;

import mainpackage.springwithdatabase.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import java.util.List;
@Component
public class PersonDAO {
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> index(){//все люди
        return jdbcTemplate.query("SELECT * FROM Person ORDER BY id_person", new BeanPropertyRowMapper<>(Person.class));
    }
    public Person show(int id){//чел по id
        return jdbcTemplate.query("SELECT * FROM Person WHERE id_person=?", new Object[]{id},//в query вставляется массив из объектов
                new BeanPropertyRowMapper<>(Person.class)).stream().findAny().orElse(null);
    }

    public void save(Person person){
        jdbcTemplate.update("INSERT INTO Person (name_person, age, email) VALUES (?, ?, ?)", person.getName_person(),
                person.getAge(), person.getEmail());
    }
    public void update(int id, Person person){
        jdbcTemplate.update("UPDATE person SET name_person=?, age=?, email=? WHERE id_person=?",//в update синтаксис vararg
                person.getName_person(), person.getAge(), person.getEmail(), id);
    }
    public void delete(int id){
        jdbcTemplate.update("DELETE FROM Person WHERE id_person=?", id);
    }
}
