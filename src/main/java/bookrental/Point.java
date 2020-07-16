package bookrental;

import javax.persistence.*;
import org.springframework.beans.BeanUtils;

import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name="Point_table")
public class Point {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private Long userId;
    private Integer point;
    private Integer userTotalPoint;
    private String status;
    private Date chgDate;
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getUserTotalPoint() {
        return userTotalPoint;
    }
    public void setUserTotalPoint(Integer userTotalPoint) {
        this.userTotalPoint = userTotalPoint;
    }



    public Point() {
        this.point = 0;
        Date date = new Date();
        this.chgDate = new Timestamp(date.getTime());
    }


    @PostPersist
    public void onPostPersist(){
        if(this.getStatus().equals("saved")){
            Saved saved = new Saved();
            BeanUtils.copyProperties(this, saved);
            saved.publishAfterCommit();

        }else if(this.getStatus().equals("used")){

            Used used = new Used();
            BeanUtils.copyProperties(this, used);
            used.publishAfterCommit();
        }

    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }
    public Date getChgDate() {
        return chgDate;
    }

    public void setChgDate(Date chgDate) {
        this.chgDate = chgDate;
    }




}
