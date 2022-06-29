package com.example.finalproject;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Pokemon implements Parcelable {

    String name, description, type1, type2, spriteURL,spriteURLBack;
    int id,  hp, atk, def, spAtk, spDef, speed, weight, height;
    boolean favorite;


    public Pokemon() {
    }

    public Pokemon(String name, String description, String type1, String type2,
                   String spriteURL, int id, int hp, int atk, int def, int spAtk,
                   int spDef, int speed, int weight, int height) {
        // JSON Object
        this.spriteURL = spriteURL;
        // position 5
        this.height = height;

        // position 7(6?)
        this.id = id;

        // position 11(10?)
        this.name = name;

        // 14(13?) link to another json e.g :"https://pokeapi.co/api/v2/pokemon-species/2/
        // position 7 of second object for an array with flavor text, grab array position 1 object 1
        // .flavor_text
        this.description = description;

        // array 17(16?)
        this.type1 = type1;
        this.type2 = type2;
        // array at 16(15?)
            this.hp = hp;
            this.atk = atk;
            this.def = def;
            this.spAtk = spAtk;
            this.spDef = spDef;
            this.speed = speed;

        // array at 18(17?)
        this.weight = weight;
        favorite = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType1() {
        return type1;
    }

    public void setType1(String type1) {
        this.type1 = type1;
    }

    public String getType2() {
        return type2;
    }

    public void setType2(String type2) {
        this.type2 = type2;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getAtk() {
        return atk;
    }

    public void setAtk(int atk) {
        this.atk = atk;
    }

    public int getDef() {
        return def;
    }

    public void setDef(int def) {
        this.def = def;
    }

    public int getSpAtk() {
        return spAtk;
    }

    public void setSpAtk(int spAtk) {
        this.spAtk = spAtk;
    }

    public int getSpDef() {
        return spDef;
    }

    public void setSpDef(int spDef) {
        this.spDef = spDef;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setInfo(JSONObject response){
        try {
            this.height = response.getInt("height");
            this.weight = response.getInt("weight");
            this.name = response.getString("name");
            this.id = response.getInt("id");
            for( int i = 0; i < response.getJSONArray("stats").length();i++){
                switch (i){
                    case 0:
                        this.hp = response.getJSONArray("stats").getJSONObject(0).getInt("base_stat");
                        break;
                    case 1:
                        this.atk = response.getJSONArray("stats").getJSONObject(1).getInt("base_stat");
                        break;
                    case 2:
                        this.def = response.getJSONArray("stats").getJSONObject(2).getInt("base_stat");
                        break;
                    case 3:
                        this.spAtk = response.getJSONArray("stats").getJSONObject(3).getInt("base_stat");
                        break;
                    case 4:
                        this.spDef = response.getJSONArray("stats").getJSONObject(4).getInt("base_stat");
                        break;
                    case 5:
                        this.speed = response.getJSONArray("stats").getJSONObject(5).getInt("base_stat");
                        break;
                    default:
                        break;
                }
                this.spriteURL = response.getJSONObject("sprites").getString("front_default");
                this.spriteURLBack = response.getJSONObject("sprites").getString("back_default");
                if(response.getJSONArray("types").length() < 2){
                    this.type1 = response.getJSONArray("types").getJSONObject(0).getJSONObject("type").getString("name");
                    this.type2 = "NONE";
                }else{
                    this.type1 = response.getJSONArray("types").getJSONObject(0).getJSONObject("type").getString("name");
                    this.type2 = response.getJSONArray("types").getJSONObject(1).getJSONObject("type").getString("name");
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    public void setDescription(JSONObject response) {
        try {
            JSONArray flavor = response.getJSONArray("flavor_text_entries");
            String desc = flavor.getJSONObject(0).getString("flavor_text").toString();
            this.description = desc;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getSpriteURL() {
        return spriteURL;
    }
    public String getSpriteURLBack() {
        return spriteURLBack;
    }

    public void setSpriteURL(String spriteURL) {
        this.spriteURL = spriteURL;
    }
    public void setSpriteURLBack(String spriteURLBack) {
        this.spriteURLBack = spriteURLBack;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(type1);
        dest.writeString(type2);
        dest.writeString(spriteURL);
        dest.writeString(spriteURLBack);
        dest.writeInt(id);
        dest.writeInt(hp);
        dest.writeInt(atk);
        dest.writeInt(def);
        dest.writeInt(spAtk);
        dest.writeInt(spDef);
        dest.writeInt(speed);
        dest.writeInt(weight);
        dest.writeInt(height);
        dest.writeByte((byte) (favorite ? 1 : 0));
    }

    public static final Parcelable.Creator<Pokemon> CREATOR = new Parcelable.Creator<Pokemon>() {
        public Pokemon createFromParcel(Parcel in) {
            return new Pokemon(in);
        }

        public Pokemon[] newArray(int size) {
            return new Pokemon[size];
        }
    };

    private Pokemon(Parcel in) {
        name = in.readString();
        description = in.readString();
        type1 = in.readString();
        type2 = in.readString();
        spriteURL = in.readString();
        spriteURLBack = in.readString();
        id = in.readInt();
        hp = in.readInt();
        atk = in.readInt();
        def = in.readInt();
        spAtk = in.readInt();
        spDef = in.readInt();
        speed = in.readInt();
        weight = in.readInt();
        height = in.readInt();
        favorite = in.readByte() != 0;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    @Override
    public String toString() {
        return "Pokemon{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", type1='" + type1 + '\'' +
                ", type2='" + type2 + '\'' +
                ", spriteURL='" + spriteURL + '\'' +
                ", spriteURLBack='" + spriteURLBack + '\'' +
                ", id=" + id +
                ", hp=" + hp +
                ", atk=" + atk +
                ", def=" + def +
                ", spAtk=" + spAtk +
                ", spDef=" + spDef +
                ", speed=" + speed +
                ", weight=" + weight +
                ", height=" + height +
                ", favorite=" + favorite +
                '}';
    }
}
