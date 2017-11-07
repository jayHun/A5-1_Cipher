// �ý��ۺ��� A5/1 ����

import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Scanner;

public class crypto {
	
	//���� �Լ� : ��ȣȭ �޼����� encode()�� ��ȣȭ �޼����� decode()�� ȣ��
	public static void main(String[] args) {
		int tmp=0;
		String put=null;
		Scanner sc1 = new Scanner(System.in);
		Scanner sc2 = new Scanner(System.in);
		boolean flag=true;
		float choose=0;
		
		// ��ȣȭ, ��ȣȭ �ݺ�
		do{
			char [] ctext = new char[64];  	// Cipher text�� ������ char�� �迭
			char [] ptext = new char[64];  	// Plain text�� ������ char�� �迭
			int [] keystream = new int[64];	// Keystream�� ������ int�� �迭
			System.out.println("===Encryption===");
			System.out.print("Insert PlainText : ");
			put = sc2.nextLine();
			encode(tmp, put, ctext, ptext, keystream);
			System.out.print("Do you want to descrypt this cipher text?\n1.Yes\n2.No\n3.Exit\n: ");
			
			//1�� ���� �� ������ Ű��Ʈ������ ��ȣȭ
			//2�� ���� �� ������ ����� Ű��Ʈ�� �ʱ�ȭ, ���ο� ��ȣȭ ����
			//3�� ���� �� ���α׷� ����
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
	
	// ��ȣȭ �޼���
	private static void encode(int tmp, String put, char []ctext, char []ptext, int []keystream){
		int [] x= new int[19];  // x ��������
		int [] y= new int[22];  // y ��������
		int [] z= new int[23];  // z ��������
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
		// keystream ��Ʈ ���
		System.out.print("keystream : ");
		for(int i=0; i<ptext.length; i++) System.out.print(keystream[i]);
		
		System.out.printf("\n"+"Cipher Text : ");
		System.out.println(String.valueOf(ctext) + "\n");
	}
	
	// ��ȣȭ �޼���
	private static void decode(int tmp, char []ctext, char []ptext, int []keystream){
		for(int i=0; i<ctext.length; i++){
			tmp=(int) (ctext[i]);
			ptext[i]=(char) (tmp^keystream[i]);		// Ű��Ʈ���� ���� Exclusive OR ����
		}
		
		System.out.printf("Plain Text : ");
		System.out.println(String.valueOf(ptext)+"\n\n\n");
	}
	
	// x, y, z �������� �ʱ�ȭ
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
	
	// 1�� 0�� ���� ���� ���ڸ� ���� �޼���
	private static int maj(int x, int y, int z){
		int cnt0=0, cnt1=0;
		
		if(x==0) cnt0+=1; else cnt1+=1;
		if(y==0) cnt0+=1; else cnt1+=1;
		if(z==0) cnt0+=1; else cnt1+=1;
		
		if(cnt0>cnt1) return 0; else return 1;
	}
}

