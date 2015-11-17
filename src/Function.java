/**
 * Created by tage on 11/17/15.
 */
public abstract class Function {
    String name;

    public abstract double function(double param);
}

class Sin extends Function {
    Sin() {
        name = "sin";
    }


    public double function(double param) {
        return Math.sin(param);
    }
}

class Cos extends Function {
    Cos() {
        name = "sin";
    }

    public double function(double param) {
        return Math.cos(param);
    }
}

class Tan extends Function {
    Tan() {
        name = "tan";
    }

    @Override
    public double function(double param) {
        return Math.tan(param);
    }
}

class Log extends Function {
    Log() {
        name = "log";
    }

    @Override
    public double function(double param) {
        return Math.log(param);
    }
}

class Exp extends Function {
    Exp() {
        name = "exp";
    }

    @Override
    public double function(double param) {
        return Math.exp(param);
    }
}

class Sqrt extends Function {
    Sqrt() {
        name = "sqrt";
    }

    @Override
    public double function(double param) {
        return Math.sqrt(param);
    }
}