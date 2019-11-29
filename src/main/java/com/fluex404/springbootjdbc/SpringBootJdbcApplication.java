package com.fluex404.springbootjdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@SpringBootApplication
public class SpringBootJdbcApplication implements CommandLineRunner {

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public SpringBootJdbcApplication(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringBootJdbcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// count
//		System.out.println(jdbcTemplate.queryForObject("select count(*) from student", Integer.class));

		// insert
//		System.out.println(jdbcTemplate.update("insert into student(`name`, age, email) values(?, ?, ?)",
//				"Test Name", 19, "test123@gmail.com"));

		// update test123@gmail.com
//		System.out.println(jdbcTemplate.update("update student t set t.job = ?, t.created_date=? where t.email = ?",
//				"Java Programmer",new Date(), "test123@gmail.com"));

		// select all data
//		jdbcTemplate.query("select * from student", (rs, i) -> {
//			return toStudent(rs, i);
//		}).forEach(System.out::println);

//		jdbcTemplate.query("select * from student", (rs, i) -> toObject(rs, i))
//		.forEach(t -> {
//			for(Object o : t){ // columnnya
//				System.out.print(o + " | ");
//			}
//			System.out.println();
//		});

//		jdbcTemplate.query("SELECT s.`id` s_id, s.`name` s_name, s.`age`, b.`id` b_id, b.`name` b_name, s.`age` * 2 AS fk_age FROM `student` s " +
//				"LEFT JOIN book b ON b.`student_id` = s.`id`", (rs, i) -> toObjectRelationShip(rs, i))
//		.forEach(t -> {
//			for(Object o : t) { // column
//				System.out.print(o + " | ");
//			}
//			System.out.println();
//		});

		// findAll By age and age and name like
		jdbcTemplate.query("select * from student s where s.age = ? and s.name like ?",
				new Object[]{19, "%i%"}, (rs, i) -> toStudent(rs, i))
				.forEach(System.out::println);

	}

	private Student toStudent(ResultSet rs, Integer i) throws SQLException {
		Student student = new Student();
		student.setId(rs.getLong("id"));
		student.setName(rs.getString("name"));

		System.out.println("index ke-"+i);

		return student;
	}

	private Object[] toObject(ResultSet rs, Integer i) throws SQLException {
		Object[] object = {
				rs.getLong("id"),
				rs.getString("name"),
				rs.getString("email"),
				rs.getInt("age"),
				rs.getString("job")};
		return object;
	}

	private Object[] toObjectRelationShip(ResultSet rs, Integer i) throws SQLException {
		Object[] object = {
				rs.getLong("s_id"),
				rs.getString("s_name"),
				rs.getString("age"),
				rs.getLong("b_id"),
				rs.getString("b_name"),
				rs.getInt("fk_age")
		};

		return object;
	};

	class Student{
		private Long id;
		private String name;

		public Student() {
		}

		public Student(Long id, String name) {
			this.id = id;
			this.name = name;
		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		@Override
		public String toString() {
			return "Student{" +
					"id=" + id +
					", name='" + name + '\'' +
					'}';
		}
	}
}
