import java.util.ArrayList;


public class GreedyFitness {
	private double[] dim1;
	private double[] dim2;
	private double[] dim3;
	private double[][] parcels;
	private double v1;
	private double v2;
	private double v3;
	public static double[] container = {16.5, 2.5, 4};
	private double[] temp;
	private ArrayList<double[]> scrapL = new ArrayList<double[]>();
	private ArrayList<double[]> scrapH = new ArrayList<double[]>();
	private ArrayList<double[]> scrapW = new ArrayList<double[]>();
	private double[] sortedItems = {0, 0, 0, 0, 0, 0}; // 0-2 ID of container; 3-5 value
	private int firstType = 0;
	private int secondType = 0;
	private int thirdType = 0;
	private double h=container[2];
	private double l=container[0];
	private int cntr1 = 0;
	private Visualization visual = new Visualization();

	public GreedyFitness(double[] dim1, double[] dim2, double[] dim3, double[] v){
		this.dim1=dim1;
		this.dim2=dim2;
		this.dim3=dim3;
		this.v1=v[0];
		this.v2=v[1];
		this.v3=v[2];
		double[][] parcels = {dim1, dim2, dim3};
		this.parcels = parcels;
	}
	
	public static void main(String[] args){
		double[] dim1 = {1, 1, 2};
		double[] dim2 = {1, 1.5, 2};
		double[] dim3 = {1.5, 1.5, 1.5};
		double[] v = {3, 4, 10};
		
		GreedyFitness test = new GreedyFitness(dim1, dim2, dim3, v);
		test.computeFitness();
		test.solve();
		test.fillScraps();
		
	}
	
	public void computeFitness(){
		
		double fitness1 = v1/(dim1[0]*dim1[1]*dim1[2]);
		double fitness2 = v2/(dim2[0]*dim2[1]*dim2[2]);
		double fitness3 = v3/(dim3[0]*dim3[1]*dim3[2]);

		if(fitness1 > fitness2 && fitness1 > fitness3){
			sortedItems[0] = 1;
			sortedItems[3] = v1;
			if(fitness2 > fitness3){
				sortedItems[1] = 2;
				sortedItems[2] = 3;
				sortedItems[4] = v2;
				sortedItems[5] = v3;
			}
			else{
				sortedItems[1] = 3;
				sortedItems[2] = 2;
				sortedItems[4] = v3;
				sortedItems[5] = v2;
				
			}
		}
		
		else if(fitness2 > fitness1 && fitness2 > fitness3){
			sortedItems[0] = 2;
			sortedItems[3] = v2;
			if(fitness1 > fitness3){
				sortedItems[1] = 1;
				sortedItems[2] = 3;
				sortedItems[4] = v1;
				sortedItems[5] = v3;
			}
			else{
				sortedItems[1] = 3;
				sortedItems[2] = 1;
				sortedItems[4] = v3;
				sortedItems[5] = v1;
				
			}
		}
		
		else if(fitness3 > fitness1 && fitness3 > fitness2){
			sortedItems[0] = 3;
			sortedItems[3] = v3;
			if(fitness1 > fitness2){
				sortedItems[1] = 1;
				sortedItems[2] = 2;
				sortedItems[4] = v1;
				sortedItems[5] = v2;
			}
			else{
				sortedItems[1] = 2;
				sortedItems[2] = 1;
				sortedItems[4] = v2;
				sortedItems[5] = v1;
				
			}
		}
		System.out.println();
		System.out.println("fitness1: " + fitness1);
		System.out.println("fitness2: " + fitness2);
		System.out.println("fitness3: " + fitness3);
		System.out.println();
		
		System.out.println(parcels[(int)sortedItems[0]-1][0]);
		System.out.println(parcels[(int)sortedItems[0]-1][1]);
		System.out.println(parcels[(int)sortedItems[0]-1][2]);
		System.out.println();

	}
	
	public void solve(){
		
		int ID = (int)sortedItems[0]-1;
		double length = parcels[ID][0];
		double width = parcels[ID][1];
		double height = parcels[ID][2];
		double leng = 0;
		
		if(this.container[2] >= height){
			cntr1++;
			double[] temp = {this.container[0], this.container[1], height};
			this.temp = temp;
			
			double[] container = {this.container[0], this.container[1], this.container[2]-height};
			this.container = container;
			
			int line = 0; 
			int level = cntr1;
			
			while(temp[1] >= width){
				line++;
				while(temp[0] >= length){			
					temp[0] = temp[0] - length;
					double[] size = {0,0,0};

					if(ID==0){
						firstType++;
						
						if((int)sortedItems[0]-1==0){
							size[0] = parcels[(int)sortedItems[0]-1][0];
							size[1] = parcels[(int)sortedItems[0]-1][1];
							size[2] = parcels[(int)sortedItems[0]-1][2];
							visual.addItem(size.clone(), line, level);
						}
						
						if((int)sortedItems[1]-1==0){
							size[0] = parcels[(int)sortedItems[1]-1][0];
							size[1] = parcels[(int)sortedItems[1]-1][1];
							size[2] = parcels[(int)sortedItems[1]-1][2];
							visual.addItem(size.clone(), line, level);
						}
						
						if((int)sortedItems[2]-1==0){
							size[0] = parcels[(int)sortedItems[2]-1][0];
							size[1] = parcels[(int)sortedItems[2]-1][1];
							size[2] = parcels[(int)sortedItems[2]-1][2];
							visual.addItem(size.clone(), line, level);
						}
						
						
					}
					if(ID==1){
						secondType++;
						
						if((int)sortedItems[0]-1==0){
							size[0] = parcels[(int)sortedItems[0]-1][0];
							size[1] = parcels[(int)sortedItems[0]-1][1];
							size[2] = parcels[(int)sortedItems[0]-1][2];
							visual.addItem(size.clone(), line, level);
						}
						
						if((int)sortedItems[1]-1==0){
							size[0] = parcels[(int)sortedItems[1]-1][0];
							size[1] = parcels[(int)sortedItems[1]-1][1];
							size[2] = parcels[(int)sortedItems[1]-1][2];
							visual.addItem(size.clone(), line, level);
						}
						
						if((int)sortedItems[2]-1==0){
							size[0] = parcels[(int)sortedItems[2]-1][0];
							size[1] = parcels[(int)sortedItems[2]-1][1];
							size[2] = parcels[(int)sortedItems[2]-1][2];
							visual.addItem(size.clone(), line, level);
						}
					}
					if(ID==2){
						thirdType++;
						
						if((int)sortedItems[0]-1==0){
							size[0] = parcels[(int)sortedItems[0]-1][0];
							size[1] = parcels[(int)sortedItems[0]-1][1];
							size[2] = parcels[(int)sortedItems[0]-1][2];
							visual.addItem(size.clone(), line, level);
						}
						
						if((int)sortedItems[1]-1==0){
							size[0] = parcels[(int)sortedItems[1]-1][0];
							size[1] = parcels[(int)sortedItems[1]-1][1];
							size[2] = parcels[(int)sortedItems[1]-1][2];
							visual.addItem(size.clone(), line, level);
						}
						
						if((int)sortedItems[2]-1==0){
							size[0] = parcels[(int)sortedItems[2]-1][0];
							size[1] = parcels[(int)sortedItems[2]-1][1];
							size[2] = parcels[(int)sortedItems[2]-1][2];
							visual.addItem(size.clone(), line, level);
						}
					}
				}
				temp[1] = temp[1] - width;
				leng=temp[0];
				temp[0] = this.container[0];
			}
			int cntr2 = (int)(l/length);
			double[] scrap1 = {container[0]-leng, temp[1], cntr1*height};	
			double[] scrap2 = {container[0]-cntr2*length, temp[1], h};
			double[] scrap3 = {container[0]-leng, container[1], container[2]};
			scrap2[1] = container[1];
			
			scrapW.add(scrap1);
			scrapL.add(scrap2);
			scrapH.add(scrap3);
			solve();
			}
			else{	
				System.out.println("Scrap length");
				System.out.println(scrapL.get(0)[0]);
				System.out.println(scrapL.get(0)[1]);
				System.out.println(scrapL.get(0)[2]);
				System.out.println();
				
				
				System.out.println("Scrap height");
				System.out.println(scrapH.get(scrapH.size()-1)[0]);
				System.out.println(scrapH.get(scrapH.size()-1)[1]);
				System.out.println(scrapH.get(scrapH.size()-1)[2]);
				System.out.println();
				
				
				System.out.println("Scrap width");
				System.out.println(scrapW.get(scrapW.size()-1)[0]);
				System.out.println(scrapW.get(scrapW.size()-1)[1]);
				System.out.println(scrapW.get(scrapW.size()-1)[2]);
				System.out.println();
				
				displayStats();
		}
	}
	public void fillScraps(){
		for(int m=0; m<3; m++){
			if(m==0)
				container=scrapW.get(scrapW.size()-1);
			if(m==1)
				container=scrapH.get(scrapH.size()-1);
			if(m==2)
				container=scrapL.get(scrapL.size()-1);
			
			System.out.println("Scrap "+ (m+1) );
			System.out.println(container[0]);
			System.out.println(container[1]);
			System.out.println(container[2]);
			System.out.println();
			
			for(int i=0; i<3; i++){
				
				int ID = (int)sortedItems[i]-1;
				double length = parcels[ID][0];
				double width = parcels[ID][1];
				double height = parcels[ID][2];
				
				for(int j=0; j<2; j++){
					if(j==1){
						double temp = height;
						height = width;
						width = temp;
					}
	
					for(int k=0; k<2; k++){
						if(k==1){
							double temp = height;
							height = length;
							length = temp;
						}
						
						while(this.container[2] >= height && this.container[1] >= width){

							this.temp = container.clone();
							
							while(this.container[0] >= length){		
								container[0] = container[0] - length;
								
								if(ID==0)
									firstType++;
								if(ID==1)
									secondType++;
								if(ID==2)
									thirdType++;
							
							}
							if(container[0] < length)
								container[2] = container[2] - height;
							
							container[0] = temp[0];

						}
					}
				}
			}
			displayStats();
			visual.start();
		}
	
	}
	
	public void displayStats(){
		System.out.println(firstType);
		System.out.println(secondType);
		System.out.println(thirdType);
		System.out.println();
		

	}
	
	
}
