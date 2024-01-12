import java.util.ArrayList;

public class Account {
     public int cookies;
     public User user;
     public Score score;
     public ArrayList<Item> items;
     public ArrayList<Assistant> assistants;

     public Account(int cookies, User user, Score score, ArrayList<Item> items, ArrayList<Assistant> assistants) {
          this.cookies = cookies;
          this.user = user;
          this.score = score;
          this.items = items;
          this.assistants = assistants;
     }

     public void tick(){

     }
     public void click(){

     }
}
