package lunaglaxe7.thaumtraveller.api;

public interface IHandler<T extends TravelEvent> {

    void handle(T event);
}
