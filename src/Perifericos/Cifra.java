package Perifericos;



//UTILIZAMOS ESTA CLASE UNICAMENTE PARA QUE LA CANTIDAD DE CIFRAS NUMERICAS
//INGRESADAS POR TECLADO NO SEA MAYOR O MENOS A LA ESPERADA

public class Cifra {

	public  int obtenerCifraInt(int n) {

		int res = 0;
		int d = 10;
		int cifra = 0;

		do {
			res = n % d;

			cifra++;

			d = d * 10;

		} while (res != n);

		return cifra;
	}
	
	public  long obtenerCifraLong(long n) {

		long res = 0;
		long d = 10;
		long cifra = 0;

		do {
			res = n % d;

			cifra++;

			d = d * 10;

		} while (res != n);

		return cifra;
	}

}