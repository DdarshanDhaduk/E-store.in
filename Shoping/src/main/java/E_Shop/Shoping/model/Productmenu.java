package E_Shop.Shoping.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Productmenu {
    @Id
    private int menu_id;

    @NotBlank(message = "name cannot be empty")
    private String name;

    @Size(min = 1, message = "fd_1 cannot be empty")
    private String fd_1;

    @Size(min = 1, message = "fd_2 cannot be empty")
    private String fd_2;

    @Size(min = 1, message = "fd_3 cannot be empty")
    private String fd_3;

    @Size(min = 1, message = "fd_4 cannot be empty")
    private String fd_4;

    @Size(min = 1, message = "d_1 cannot be empty")
    private String d_1;

    @Size(min = 1, message = "d_2 cannot be empty")
    private String d_2;

    @Size(min = 1, message = "d_3 cannot be empty")
    private String d_3;

    @Size(min = 1, message = "d_4 cannot be empty")
    private String d_4;

    @Size(min = 1, message = "d_5 cannot be empty")
    private String d_5;

    @Size(min = 1, message = "d_6 cannot be empty")
    private String d_6;

    private String d_7;
    private String d_8;
    private String d_9;
    private String d_10;
    private String d_11;
    private String d_12;
    private String d_13;
    private String d_14;
    private String d_15;

    @Lob
    @Column(name = "img", columnDefinition = "LONGBLOB")
    private byte[] img;

    @OneToMany(cascade ={CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH},mappedBy = "productmenu")
    private List<Product> products=new ArrayList<>();

    public Map<String,String> getMenuMap(){
        Map<String,String> menu=new HashMap<>();
        menu.put("fd_1",fd_1);
        menu.put("fd_2",fd_2);
        menu.put("fd_3",fd_3);
        menu.put("fd_4",fd_4);
        menu.put("d_1",d_1);
        menu.put("d_2",d_2);
        menu.put("d_3",d_3);
        menu.put("d_4",d_4);
        menu.put("d_5",d_5);
        menu.put("d_6",d_6);
        menu.put("d_7",d_7);
        menu.put("d_8",d_8);
        menu.put("d_9",d_9);
        menu.put("d_10",d_10);
        menu.put("d_11",d_11);
        menu.put("d_12",d_12);
        menu.put("d_13",d_13);
        menu.put("d_14",d_14);
        menu.put("d_15",d_15);

        return menu;
    }
}
