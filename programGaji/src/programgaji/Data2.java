/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package programgaji;

/**
 *
 * @author Haikal Febriano
 */
public class Data2 {

    private Integer id;
    private String nama;
    private String gaji;
    private String lembur;
    private String total;

    public Data2(Integer id, String nama, String gaji, String lembur, String total) {
        this.id = id;
        this.nama = nama;
        this.gaji = gaji;
        this.lembur = lembur;
        this.total = total;
    }

    public Integer getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    public String getGaji() {
        return gaji;
    }

    public String getLembur() {
        return lembur;
    }

    public String getTotal() {
        return total;
    }

}