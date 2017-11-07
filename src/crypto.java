// 시스템보안 A5/1 구현

import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Scanner;

public class crypto {
	
	//메인 함수 : 암호화 메서드인 encode()와 복호화 메서드인 decode()를 호출
	public static void main(String[] args) {
		int tmp=0;
		String put=null;
		Scanner sc1 = new Scanner(System.in);
		Scanner sc2 = new Scanner(System.in);
		boolean flag=true;
		float choose=0;
		
		// 암호화, 복호화 반복
		do{
			char [] ctext = new char[64];  	// Cipher text를 저장할 char형 배열
			char [] ptext = new char[64];  	// Plain text를 저장할 char형 배열
			int [] keystream = new int[64];	// Keystream를 저장할 int형 배열
			System.out.println("===Encryption===");
			System.out.print("Insert PlainText : ");
			put = sc2.nextLine();
			encode(tmp, put, ctext, ptext, keystream);
			System.out.print("Do you want to descrypt this cipher text?\n1.Yes\n2.No\n3.Exit\n: ");
			
			//1번 선택 시 동일한 키스트림으로 복호화
			//2번 선택 시 이전에 사용한 키스트림 초기화, 새로운 암호화 시작
			//3번 선택 시 프로그램 종료
			try{
				choose=sc1.nextFloat();
				if(choose==1){
					System.out.println("\n===Decryption===");
					decode(tmp, ctext, ptext, keystream);
					continue;
				}else if(choose==2){
					System.out.println("\n\n");
					continue;
				}else
					System.out.println("bye~");
					flag=false;
			}catch(NoSuchElementException | IllegalArgumentException e){
				System.out.println("Error curred : "+e);
				sc1.next();
				continue;
			}
		}while(flag);
		sc1.close();
		sc2.close();
	}
	
	// 암호화 메서드
	private static void encode(int tmp, String put, char []ctext, char []ptext, int []keystream){
		int [] x= new int[19];  // x 레지스터
		int [] y= new int[22];  // y 레지스터
		int [] z= new int[23];  // z 레지스터
		int m, t;
		
		ptext=put.toCharArray();
		
		System.out.println("");
		initRG(x, y, z);
		
		for(int i=0; i<ptext.length; i++){
			m=maj(x[8], y[10], z[10]);
			if(x[8]==m){
				t=x[13]^x[16]^x[17]^x[18];
				for(int j=18; j>0; j--) x[j] = x[j-1];
				x[0] = t;
			}
			
			if(y[10]==m){
				t=y[20]^y[21];
				for(int j=21; j>0; j--) y[j] = y[j-1];
				y[0] = t;
			}
			
			if(z[10]==m){
				t=z[7]^z[20]^z[21]^z[22];
				for(int j=22; j>0; j--) z[j] = z[j-1];
				z[0] = t;
			}
			keystream[i] = x[18]^y[21]^z[22];
		}
		
		for(int i=0; i<ptext.length; i++){
			tmp=(int) (ptext[i]);
			ctext[i]=(char) (tmp^keystream[i]);
		}
		// keystream 비트 출력
		System.out.print("keystream : ");
		for(int i=0; i<ptext.length; i++) System.out.print(keystream[i]);
		
		System.out.printf("\n"+"Cipher Text : ");
		System.out.println(String.valueOf(ctext) + "\n");
	}
	
	// 복호화 메서드
	private static void decode(int tmp, char []ctext, char []ptext, int []keystream){
		for(int i=0; i<ctext.length; i++){
			tmp=(int) (ctext[i]);
			ptext[i]=(char) (tmp^keystream[i]);		// 키스트림과 문자 Exclusive OR 연산
		}
		
		System.out.printf("Plain Text : ");
		System.out.println(String.valueOf(ptext)+"\n\n\n");
	}
	
	// x, y, z 레지스터 초기화
	private static void initRG(int x[], int y[], int z[]){
		Random rd = new Random();
		for(int i=0; i<19; i++) x[i] = rd.nextInt(2);
		for(int i=0; i<22; i++) y[i] = rd.nextInt(2);
		for(int i=0; i<23; i++) z[i] = rd.nextInt(2);
		System.out.print("x :");
		for(int i=0; i<19; i++) System.out.print(x[i]);
		System.out.println("");
		System.out.print("y :");
		for(int i=0; i<22; i++) System.out.print(y[i]);
		System.out.println("");
		System.out.print("z :");
		for(int i=0; i<23; i++) System.out.print(z[i]);
		System.out.println("");
	}
	
	// 1과 0중 가장 많은 숫자를 고르는 메서드
	private static int maj(int x, int y, int z){
		int cnt0=0, cnt1=0;
		
		if(x==0) cnt0+=1; else cnt1+=1;
		if(y==0) cnt0+=1; else cnt1+=1;
		if(z==0) cnt0+=1; else cnt1+=1;
		
		if(cnt0>cnt1) return 0; else return 1;
	}
}

