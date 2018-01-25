package jdbcdemo.jdbcdemo;

import java.util.List;

import jdbcdemo.dao.PersonRepository;
import jdbcdemo.dao.mappers.PersonResultMapper;
import jdbcdemo.domain.Person;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	System.out.println( "Początek" );
    	
    	PersonResultMapper mapkier = new PersonResultMapper();
    	
		PersonRepository repo = new PersonRepository(mapkier);
    	repo.createTable();
    	
    	Person janek = new Person();
    	janek.setAge(30);
    	janek.setName("Jan");
    	janek.setSurname("Kowalski");
    	
    	repo.add(janek);
    	
    	List<Person> people = repo.getAll();
    	for(Person p : people){
    		System.out.println(p.getId()+"\t" 
    				+ p.getName()+"\t"
    				+ p.getSurname()+"\t"
    				+ p.getAge());
    	}
    }
}
