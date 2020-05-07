package id.ac.umn.quiz1;

/**
 * Created by aldo_ on 20/03/2018.
 */

public class Member {
    private int _id;
    private String _name, _position;

    public Member(int id, String name, String position){
        this._id = id;
        this._name = name;
        this._position = position;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public String get_position() {
        return _position;
    }

    public void set_position(String _position) {
        this._position = _position;
    }
}
