package br.com.fiap.hospitalja.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Hospital implements Parcelable {

    private int codigo;
    private String nome;
    private String endereco;
    private String fila;
    private String tempoEspera;
    private String latitude;
    private String longitude;
    private List<Especialidade> especialidades;



    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getFila() {
        return fila;
    }

    public void setFila(String fila) {
        this.fila = fila;
    }

    public String getTempoEspera() {
        return tempoEspera;
    }

    public void setTempoEspera(String tempoEspera) {
        this.tempoEspera = tempoEspera;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public List<Especialidade> getEspecialidades() {
        return especialidades;
    }

    public void setEspecialidades(List<Especialidade> especialidades) {
        this.especialidades = especialidades;
    }

    public Hospital(Parcel parcel) {
        this.codigo = parcel.readInt();
        this.nome = parcel.readString();
        this.endereco = parcel.readString();
        this.fila = parcel.readString();
        this.tempoEspera = parcel.readString();
        this.latitude = parcel.readString();
        this.longitude = parcel.readString();
        this.especialidades = new ArrayList<Especialidade>();
        parcel.readList(this.especialidades, Especialidade.class.getClassLoader());
    }

    public Hospital() {
    }

    public Hospital(int codigo, String nome, String endereco, String fila, String tempoEspera, String latitude, String longitude, List<Especialidade> especialidades) {
        this.codigo = codigo;
        this.nome = nome;
        this.endereco = endereco;
        this.fila = fila;
        this.tempoEspera = tempoEspera;
        this.latitude = latitude;
        this.longitude = longitude;
        this.especialidades = especialidades;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(codigo);
        dest.writeString(nome);
        dest.writeString(endereco);
        dest.writeString(fila);
        dest.writeString(tempoEspera);
        dest.writeString(latitude);
        dest.writeString(longitude);
        dest.writeList(especialidades);
    }



    public static final Parcelable.Creator<Hospital> CREATOR = new Parcelable.Creator<Hospital>() {
        @Override
        public Hospital createFromParcel(Parcel in) {
            return new Hospital(in);
        }

        @Override
        public Hospital[] newArray(int size) {
            return new Hospital[size];
        }
    };
}
