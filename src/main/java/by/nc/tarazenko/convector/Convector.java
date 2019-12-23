package by.nc.tarazenko.convector;

interface Convector<A,B> {
    A toDTO(B entity);
    B fromDTO(A entity);
}
