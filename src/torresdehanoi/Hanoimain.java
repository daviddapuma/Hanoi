package torresdehanoi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class Hanoimain {

	public static void main(String[] args) {
		int discos = 0;
		int rMDes = 0;// radio del mayor disco descolocado
		int id=0;
		int heuristica=0;
		int movimientos = 0;
		int mayorDiscoColocadoenC = -2;
		boolean caminoEncontrado = false;
		ArrayList<Estado> abiertos = new ArrayList<Estado>();
        ArrayList<Estado> cerrados = new ArrayList<Estado>();
        ArrayList<Estado> caminoFinal = new ArrayList<Estado>();
        ArrayList<Integer> columnaA = new ArrayList<Integer>();
        ArrayList<Integer> columnaC = new ArrayList<Integer>();
		Scanner sc = new Scanner(System.in);
		System.out.printf("Introduzca el numero de discos que desea: ");
		discos= sc.nextInt();
		rMDes = discos;
		//System.out.printf("%d discos deseados\n",discos);
		heuristica = (int) Math.pow(2, rMDes-1);
				
		//Creación del estado inicial
		for (int i=discos;i>0;i--){
			columnaA.add(i);
			columnaC.add(i);
		}
		
		Estado estadoInicial = new Estado(0, 0, movimientos, heuristica);// De este estado  partimos para hacer cosas
        estadoInicial.setColA(columnaA);
        Estado estadoFinal = new Estado(-1, -1, movimientos, heuristica);// De este estado  partimos para hacer cosas
        estadoFinal.setColC(columnaC);
		abiertos.add(estadoInicial);
		
		while(caminoEncontrado == false){
			Estado actual = new Estado();
			Estado siguienteizder = new Estado();
			Estado siguientederiz = new Estado();
			Estado siguienteizmed = new Estado();
			Estado siguientemediz = new Estado();
			Estado siguientemedder = new Estado();
			Estado siguientedermed = new Estado();
			
			actual=setValuesEstado(actual,abiertos.get(0));
			cerrados.add(actual);
			abiertos.remove(0);
			
			if(cerrados.get(cerrados.size()-1).getColC()!= null &&cerrados.get(cerrados.size()-1).getColC().equals(estadoFinal.getColC())){
				caminoEncontrado = true;
			}
			else{             
				siguienteizmed = izq_a_medio(actual);
				if(siguienteizmed!=null){
					if(!no_miembro(abiertos, cerrados, siguienteizmed)){
						id+=1;
						siguienteizmed.setId(id);
						siguienteizmed.setAnterior(actual.getId());
						siguienteizmed.setG(actual.getG()+1);
						abiertos.add(siguienteizmed);
						Collections.sort(abiertos, MakeComparator);
					}
				}
				
				siguientemediz = medio_a_izq(actual);
				if(siguientemediz!=null){
					if(!no_miembro(abiertos,cerrados, siguientemediz)){
						id+=1;
						siguientemediz.setId(id);
						siguientemediz.setAnterior(actual.getId());
						siguientemediz.setG(actual.getG()+1);
						abiertos.add(siguientemediz);
						Collections.sort(abiertos, MakeComparator);
					}
				}
				
				siguienteizder = izq_a_der(actual);
				if(siguienteizder!=null){
					if(!no_miembro(abiertos,cerrados, siguienteizder)){
						id+=1;
						siguienteizder.setId(id);
						siguienteizder.setAnterior(actual.getId());
						siguienteizder.setG(actual.getG()+1);
						//Control de si disco colocado es rmDes
                        if(!siguienteizder.getColC().isEmpty()){
                            if(rMDes == siguienteizder.getColC().get(siguienteizder.getColC().size()-1)){
                            	System.out.println("Coloque el rmdes");
                            	mayorDiscoColocadoenC=rMDes;
                                rMDes = rMDes - 1;
                                heuristica = (int) Math.pow(2, rMDes-1);
                                siguienteizder.setH(heuristica);
                                mostrarColumnasDeEstado(siguienteizder);
                            }   
                        }
						abiertos.add(siguienteizder);
						Collections.sort(abiertos, MakeComparator);
					}
				}
				
				siguientemedder = medio_a_der(actual);
				if(siguientemedder!=null){
					if(!no_miembro(abiertos,cerrados, siguientemedder)){
						id+=1;
						siguientemedder.setId(id);
						siguientemedder.setAnterior(actual.getId());
						siguientemedder.setG(actual.getG()+1);
						if(!siguientemedder.getColC().isEmpty()){
                            if(rMDes == siguientemedder.getColC().get(siguientemedder.getColC().size()-1)){
                            	System.out.println("Coloque el rmdes");
                            	mayorDiscoColocadoenC=rMDes; 
                            	rMDes = rMDes - 1;
                                heuristica = (int) Math.pow(2, rMDes-1);
                                siguientemedder.setH(heuristica);
                                mostrarColumnasDeEstado(siguientemedder); 
                            }   
                        }
						abiertos.add(siguientemedder);
						Collections.sort(abiertos, MakeComparator);
					}
				}
				
				//Control de no estar haciendo movimiento innecesario moviendo el mayor disco colocado en C
				if(!actual.getColC().isEmpty()){
					if(mayorDiscoColocadoenC!=actual.getColC().get(actual.getColC().size()-1)){
						siguientederiz = der_a_izq(actual);
						if(siguientederiz!=null){
							if(!no_miembro(abiertos,cerrados, siguientederiz)){
								id+=1;
								siguientederiz.setId(id);
								siguientederiz.setAnterior(actual.getId());
								siguientederiz.setG(actual.getG()+1);
								abiertos.add(siguientederiz);
								Collections.sort(abiertos, MakeComparator);
							}
						}
					}
				}
				
				//Control de no estar haciendo movimiento innecesario moviendo el mayor disco colocado en C
				if(!actual.getColC().isEmpty()){
					if(mayorDiscoColocadoenC!=actual.getColC().get(actual.getColC().size()-1)){
						siguientedermed= der_a_medio(actual);
						if(siguientedermed!=null){
							if(!no_miembro(abiertos,cerrados, siguientedermed)){
								id+=1;
								siguientedermed.setId(id);
								siguientedermed.setAnterior(actual.getId());
								siguientedermed.setG(actual.getG()+1);
								abiertos.add(siguientedermed);
								Collections.sort(abiertos, MakeComparator);
							}
						}
					}
				}
			}
		}// fin while
                
            mostrarCamino(cerrados);
	}
	
	public static Estado setValuesEstado(Estado dondeAñado, Estado dondeSaco){
		dondeAñado.setAnterior(dondeSaco.getAnterior());
		dondeAñado.setG(dondeSaco.getG());
		dondeAñado.setH(dondeSaco.getH());
		dondeAñado.setId(dondeSaco.getId());
                
		for(int i=0;i<dondeSaco.getColA().size(); i++){
			int valor= dondeSaco.getColA().get(i);
			dondeAñado.getColA().add(valor);
		}
		for(int i=0;i<dondeSaco.getColB().size(); i++){
			int valor= dondeSaco.getColB().get(i);
			dondeAñado.getColB().add(valor);
		}
		for(int i=0;i<dondeSaco.getColC().size(); i++){
			int valor= dondeSaco.getColC().get(i);
			dondeAñado.getColC().add(valor);
		}
		
		return dondeAñado;	
	}

	public static Estado izq_a_medio(Estado actual){
		int discoAMover;
		int discoEnDestino;
		Estado nuevo;
		
		nuevo = new Estado(-1,-1, actual.getG()+1, actual.getH());

		nuevo = setValuesEstado(nuevo,actual);
		
		if(nuevo.getColA().isEmpty()){
			return null;
		}
		else{
			discoAMover = nuevo.getColA().get(nuevo.getColA().size()-1);
			if(nuevo.getColB().isEmpty()){
				nuevo.getColB().add(discoAMover);
				nuevo.getColA().remove(nuevo.getColA().size()-1);
				return nuevo;
			}
			else{
				discoEnDestino = nuevo.getColB().get(nuevo.getColB().size()-1);
				if(discoAMover<discoEnDestino){
					nuevo.getColB().add(discoAMover);
					nuevo.getColA().remove(actual.getColA().size()-1);
					return nuevo;
				}
                                else{
                                    return null;
                                }
					
			}
		}
	}
	
	public static Estado izq_a_der(Estado actual){
		int discoAMover;
		int discoEnDestino;
		Estado nuevo;
		
		nuevo = new Estado(-1,-1,actual.getG()+1, actual.getH());
		nuevo = setValuesEstado(nuevo, actual);
		
		if(actual.getColA().isEmpty()){	
			return null;
		}
		else{
			discoAMover = actual.getColA().get(actual.getColA().size()-1);
			if(actual.getColC().isEmpty()){
				nuevo.getColC().add(discoAMover);
				nuevo.getColA().remove(actual.getColA().size()-1);
				return nuevo;
			}
			else{
				discoEnDestino = actual.getColC().get(actual.getColC().size()-1);
				if(discoAMover<discoEnDestino){
					nuevo.getColC().add(discoAMover);
					nuevo.getColA().remove(actual.getColA().size()-1);
					return nuevo;
				}else{
                                    return null;
                                }
					
			}
		}
	}
	
	public static Estado medio_a_izq(Estado actual){
		int discoAMover;
		int discoEnDestino;
		Estado nuevo;
		
		nuevo = new Estado(-1,-1,actual.getG()+1, actual.getH());
		nuevo = setValuesEstado(nuevo, actual);
		
		if(actual.getColB().isEmpty()){
			return null;
		}
		else{
			discoAMover = actual.getColB().get(actual.getColB().size()-1);
			if(actual.getColA().isEmpty()){
				nuevo.getColA().add(discoAMover);
				nuevo.getColB().remove(actual.getColB().size()-1);
				return nuevo;
			}//Columna destino tiene un disco ver si movimiento es posible en función de tamaño
			else{
				discoEnDestino = actual.getColA().get(actual.getColA().size()-1);
				if(discoAMover<discoEnDestino){
					nuevo.getColA().add(discoAMover);
					nuevo.getColB().remove(actual.getColB().size()-1);
					return nuevo;
				}else
					return null;
			}
		}
	}
	
	public static Estado medio_a_der(Estado actual){
		int discoAMover;
		int discoEnDestino;
		Estado nuevo;
		
		nuevo = new Estado(-1,-1,actual.getG()+1, actual.getH());
		nuevo = setValuesEstado(nuevo, actual);

		if(actual.getColB().isEmpty()){
			return null;
		}
		else{//Columna origen no vacia cojo el disco de arriba que es el que moveremos
			discoAMover = actual.getColB().get(actual.getColB().size()-1);
			if(actual.getColC().isEmpty()){
				nuevo.getColC().add(discoAMover);
				nuevo.getColB().remove(actual.getColB().size()-1);
				return nuevo;
			}//Columna destino tiene un disco ver si movimiento es posible en función de tamaño
			else{
				discoEnDestino = actual.getColC().get(actual.getColC().size()-1);
				if(discoAMover<discoEnDestino){
					nuevo.getColC().add(discoAMover);
					nuevo.getColB().remove(actual.getColB().size()-1);
					return nuevo;
				}else
					return null;
			}
		}
	}
	
	public static Estado der_a_izq(Estado actual){
		int discoAMover;
		int discoEnDestino;
		Estado nuevo;
	
		nuevo = new Estado(-1,-1,actual.getG()+1, actual.getH());
		nuevo = setValuesEstado(nuevo, actual);
		//nuevo.setG(nuevo.getG()+1);
		
		if(actual.getColC().isEmpty()){
			return null;
		}
		else{//Columna origen no vacia cojo el disco de arriba que es el que moveremos
			discoAMover = actual.getColC().get(actual.getColC().size()-1);
			if(actual.getColA().isEmpty()){
				//Movimiento posible porque destino no tiene discos
				nuevo.getColA().add(discoAMover);
				nuevo.getColC().remove(actual.getColC().size()-1);
				return nuevo;
			}//Columna destino tiene un disco ver si movimiento es posible en función de tamaño
			else{
				discoEnDestino = actual.getColA().get(actual.getColA().size()-1);
				if(discoAMover<discoEnDestino){
					nuevo.getColA().add(discoAMover);
					nuevo.getColC().remove(actual.getColC().size()-1);
					return nuevo;
				}else
					return null;
			}
		}
	}
	
	public static Estado der_a_medio(Estado actual){
		int discoAMover;
		int discoEnDestino;
		Estado nuevo;
		
		nuevo = new Estado(-1,-1,actual.getG()+1, actual.getH());
		nuevo = setValuesEstado(nuevo, actual);
		//nuevo.setG(nuevo.getG()+1);
		if(actual.getColC().isEmpty()){
			return null;
		}
		else{//Columna origen no vacia cojo el disco de arriba que es el que moveremos
			discoAMover = actual.getColC().get(actual.getColC().size()-1);
			if(actual.getColB().isEmpty()){
				//Movimiento posible porque destino no tiene discos
				nuevo.getColB().add(discoAMover);
				nuevo.getColC().remove(actual.getColC().size()-1);
				return nuevo;
			}//Columna destino tiene un disco ver si movimiento es posible en función de tamaño
			else{
				discoEnDestino = actual.getColB().get(actual.getColB().size()-1);
				if(discoAMover<discoEnDestino){
					nuevo.getColB().add(discoAMover);
					nuevo.getColC().remove(actual.getColC().size()-1);
					return nuevo;
				}else
					return null;
			}
		}
	}
	
public static Boolean no_miembro(ArrayList<Estado> listaAbiertos, ArrayList<Estado> listaCerrados, Estado siguiente){
		
		Boolean encontrado = false;
		
		
		for(int i =0;i<listaAbiertos.size();i++){
			if(siguiente.getColA().equals(listaAbiertos.get(i).getColA()) && siguiente.getColB().equals(listaAbiertos.get(i).getColB()) && siguiente.getColC().equals(listaAbiertos.get(i).getColC())){
				encontrado= true;
				return encontrado;
			}
		}
		
		for(int i =0;i<listaCerrados.size();i++){
			if(siguiente.getColA().equals(listaCerrados.get(i).getColA()) && siguiente.getColB().equals(listaCerrados.get(i).getColB()) && siguiente.getColC().equals(listaCerrados.get(i).getColC())){
				encontrado= true;
				return encontrado;
			}
		}
		
		return encontrado;
	}
	
	public static void mostrarColumnasDeEstado(Estado s){
		System.out.printf("Id: %d, movs: %d, anterior: %d F: %d", s.getId(), s.getG(), s.getAnterior(), s.getF());
		System.out.printf("\nA:");
		for(int x=0;x<s.getColA().size();x++) {
			  System.out.printf("|%d|" ,s.getColA().get(x));
			}
		
		System.out.printf("\nB:");
		for(int x=0;x<s.getColB().size();x++) {
			System.out.printf("|%d|" ,s.getColB().get(x));
			}
		System.out.printf("\nC:");
		for(int x=0;x<s.getColC().size();x++) {
			System.out.printf("|%d|" ,s.getColC().get(x));
			}
		System.out.printf("\n");
		
	}
	
	public static void mostrarColumnasDeEstadoEnUnaLinea (Estado s){
		System.out.printf("Id: %d, movs: %d, anterior: %d ", s.getId(), s.getG(), s.getAnterior());
		System.out.printf("estado([ ");
		for(int x=s.getColA().size()-1;x >=0;x--) {
			  System.out.printf("%d " ,s.getColA().get(x));
			}
		System.out.printf("]");
		System.out.printf(", [ ");
		for(int x=s.getColB().size()-1;x >=0;x--) {
			System.out.printf("%d " ,s.getColB().get(x));
			}
                System.out.printf("]");
		System.out.printf(", [ ");
		for(int x=s.getColC().size()-1;x >=0;x--) {
			System.out.printf("%d " ,s.getColC().get(x));
			}
                System.out.printf("])");
		System.out.printf("\n");
		
	}
	
	public static void mostrarListaAbiertos(ArrayList<Estado> listaAbiertos){
		for(int i =0; i< listaAbiertos.size(); i++){
			mostrarColumnasDeEstadoEnUnaLinea(listaAbiertos.get(i));
			
		}
		
	}
        
        public static void mostrarListaCamino(ArrayList<Estado> camino){
		for(int i =camino.size()-1; i>=0; i--){
			mostrarColumnasDeEstadoEnUnaLinea(camino.get(i));
			
		}
	}
	
        public static void mostrarCamino(ArrayList<Estado> lista){
                ArrayList<Estado> camino = new ArrayList<Estado>();
                camino.add(lista.get(lista.size()-1));
                
		for(int i =lista.size()-1; i>=0; i--){
                    if(lista.get(i).getId() == camino.get(camino.size()-1).getAnterior()){
                        camino.add(lista.get(i));
                    }
		}
		
                mostrarListaCamino(camino);
	}
	
	public static Comparator<Estado> MakeComparator = new Comparator<Estado>() {
        public int compare(Estado a, Estado b){
           
            Integer ordena =  a.getF();
            Integer ordenb =  b.getF();
 
            return ordena.compareTo(ordenb);
        }
    };

}
