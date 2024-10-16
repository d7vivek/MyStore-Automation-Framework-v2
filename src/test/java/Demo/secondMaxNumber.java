package Demo;

public class secondMaxNumber {

	public static void main(String[] args) {


		int a[]= {10,70,30,40,50,41};
		int max = 0;
		int secondMax=0;
		for(int i = 0;i<=a.length-1;i++) {
			for(int j = 1; j<=i;j++) {
				
				if(a[i]<a[j]) {
					  max =a[i];
				}

			}
			
		}
		
		for(int i = 0;i<=a.length-1;i++) {
			for(int j = 1; j<=i;j++) {
				
				if(a[i]<max) {
					secondMax=a[i];
				}

			}
			
		}

		
		System.out.println("maximum number"+max);
		System.out.println("Second max number"+secondMax);

	}
}
