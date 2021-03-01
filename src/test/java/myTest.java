import com.larry.dao.BooksMapper;
import com.larry.pojo.Books;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.InputStream;
import java.util.List;

/**
 * @author larry
 * @create 2021-03-01 12:42
 */
public class myTest {
    @Test
    public void test() {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        BooksMapper booksMapper = context.getBean("booksMapper", BooksMapper.class);
        List<Books> books = booksMapper.queryBooks();
        System.out.println(books);
    }
}
