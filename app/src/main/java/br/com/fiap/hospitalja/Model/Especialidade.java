package br.com.fiap.hospitalja.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"codigo", "descricao"})
public class Especialidade implements Parcelable {
    private int codigo;
    private String nome;
    private String descricao;



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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Especialidade() {
    }

    public Especialidade(int codigo, String nome, String descricao) {
        this.codigo = codigo;
        this.nome = nome;
        this.descricao = descricao;
    }


    public Especialidade(Parcel in) {
        codigo = in.readInt();
        nome = in.readString();
        descricao = in.readString();
    }

    public static final Creator<Especialidade> CREATOR = new Creator<Especialidade>() {
        @Override
        public Especialidade createFromParcel(Parcel in) {
            return new Especialidade(in);
        }

        @Override
        public Especialidade[] newArray(int size) {
            return new Especialidade[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(codigo);
        dest.writeString(nome);
        dest.writeString(descricao);
    }
}
