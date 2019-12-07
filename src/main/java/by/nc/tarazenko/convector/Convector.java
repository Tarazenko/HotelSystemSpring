package by.nc.tarazenko.convector;

public interface Convector<A,B> {
    A toDTO(B entity);
    B fromDTO(A entity);
}
