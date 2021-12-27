import com.revature.project1.model.Student;
import com.revature.project1.persistence.DaoManager;
import com.revature.project1.services.Service;

public class Driver{
     static DaoManager daoManager;
public Driver(DaoManager daoManager){
    this.daoManager = daoManager;
}
    
   static Student student = new Student(1, "Zeyad", "Zeyad");
 // static Student student = new Student(2, "Jon", "Jon");

    public static void main(String[] args) throws Exception {

        daoManager.create(student);  // create
        System.out.println(daoManager.read("2"));// read

        //daoService.put(student);
        //daoService.update(student); // update
        // daoService.delete(student); // delete
    }
}
