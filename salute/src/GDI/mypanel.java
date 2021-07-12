package GDI;

getImage();
	int iH = 20;
	int iW = 20;
	int iQW = 10;
	int iQH = 10;
	
	public mypanel() {
		
	}
	int [][]chessdata;
	public mypanel(int [][]a) {
		chessdata=a;
	}
	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		super.paint(g);
		g.drawImage(imageboard,0,0,null);
		for(int i=0;i<chessdata.length;i++) {
			for(int j=0;j<chessdata[0].length;j++){		
				
				
				int x=i*20+iH-iQH;
			
				int y=j*20+iW-iQW;
				if(chessdata[i][j]==1)
					g.drawImage(img1,x,y,null);
				else if(chessdata[i][j]==2)
					g.drawImage(img2,x,y,null);
				
			}
		}
	}
	
	
}
