/*
 * Copyright (C) 2012 Chris Hallson
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package jmusic.ui;

import img.LoadImage;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import jmusic.jMusicController;
import jmusic.playback.MusicPlayer;
import jmusic.playlist.table.MusicTableModel;
import jmusic.playlist.table.Row;

/**
 * The main window of the music player
 */
public class jMusic extends javax.swing.JFrame {
	// Timer for updating the progress bar
	Timer timer = null;
	boolean cleared = true;	/** Indicates that the previously playing media data has been cleared */
	
	// Icons used for buttons
	ImageIcon imagePlay;
	ImageIcon imagePause;
	ImageIcon imageStop;
	ImageIcon imageForward;
	ImageIcon imageBackward;
	ImageIcon imageSettings;
	ImageIcon imageIcon;
	// Current album art image
	Image albumImage;
	
	/**
	 * Creates new form MainWindow
	 */
	public jMusic() {
		initComponents();
		
		imagePlay = LoadImage.load("start.png");
		imagePause = LoadImage.load("pause.png");
		imageStop = LoadImage.load("stop.png");
		imageBackward = LoadImage.load("backward.png");
		imageForward = LoadImage.load("forward.png");
		imageSettings = LoadImage.load("settings.png");
		imageIcon = LoadImage.load("icon.png");
		
		albumImage = null;
		
		// Set icon for the frame
		this.setIconImage(imageIcon.getImage());
		// Set the button icons
		jToggleButtonPlay.setIcon(imagePlay);
		jToggleButtonStop.setIcon(imageStop);
		jToggleButtonBackward.setIcon(imageBackward);
		jToggleButtonForward.setIcon(imageForward);
		jButtonSettings.setIcon(imageSettings);
		// Only allow one row to be selected in the playlist
		jTablePlaylist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		// Load the media folder name out of settings
		final String folder = jMusicController.getDataStorage().get("MusicFolder");
		// If we have a folder then create a playlist
		if (folder != null){
			SwingUtilities.invokeLater(new Runnable(){
				@Override
				public void run(){
					File path = new File(folder);
					loadMusicFiles(path);
				}
			});
		}
		// Timer to update progress bars and button icons
		timer = new Timer(100, new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				MusicPlayer mp = jMusicController.getMusicPlayer();
				if (mp.isPlaying()){
					int curTime = (int)(mp.getSongPosition() / 1000000);
					int endTime = (int)(mp.getSongLength() / 1000000);
					String strCurTime = (curTime / 60) + ":" + String.format("%02d",curTime % 60);
					String strEndTime = (endTime / 60) + ":" + String.format("%02d",endTime % 60);
					
					jProgressBar1.setString(strCurTime + " / " + strEndTime);
					jProgressBar1.setMaximum(endTime);
					jProgressBar1.setValue(curTime);

					if (!jMusicController.getMusicPlayer().isPaused()){
						jToggleButtonPlay.setSelected(true);
						setPlayingIcon();
					} else {
						setPauseIcon();
					}
					
					cleared = false;
				} else {
					if (!cleared){
						jProgressBar1.setString("");
						jProgressBar1.setMinimum(0);
						jProgressBar1.setMaximum(0);
						jProgressBar1.setValue(0);
						jToggleButtonPlay.setSelected(false);
						setPlayIcon();
						setAlbumArt(null);
						setMetadata(null);
						
						cleared = true;
					}
				}
			}
		});
		timer.start();
	}

	private void setPlayIcon(){
		jToggleButtonPlay.setIcon(imagePlay);
		jToggleButtonPlay.setRolloverIcon(imagePlay);
		jToggleButtonPlay.setRolloverSelectedIcon(imagePlay);
		jToggleButtonPlay.setSelected(false);
	}
	
	private void setPlayingIcon(){
		jToggleButtonPlay.setIcon(imagePlay);
		jToggleButtonPlay.setRolloverIcon(imagePause);
		jToggleButtonPlay.setRolloverSelectedIcon(imagePause);
		jToggleButtonPlay.setSelected(true);
	}
	
	private void setPauseIcon(){
		jToggleButtonPlay.setIcon(imagePause);
		jToggleButtonPlay.setRolloverIcon(imagePlay);
		jToggleButtonPlay.setRolloverSelectedIcon(imagePlay);
		jToggleButtonPlay.setSelected(true);
	}
	
	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jToggleButtonBackward = new javax.swing.JToggleButton();
        jToggleButtonPlay = new javax.swing.JToggleButton();
        jToggleButtonStop = new javax.swing.JToggleButton();
        jToggleButtonForward = new javax.swing.JToggleButton();
        jProgressBar1 = new javax.swing.JProgressBar();
        jButtonSettings = new javax.swing.JButton();
        jSplitPane1 = new javax.swing.JSplitPane();
        jScrollPanePlaylist = new javax.swing.JScrollPane();
        jTablePlaylist = new JTableAltRows();
        jPanelSidebar = new javax.swing.JPanel();
        jLabelAlbumArt = new javax.swing.JLabel();
        jScrollPaneMetadata = new javax.swing.JScrollPane();
        jListMediaMetadata = new javax.swing.JList();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("jMusic");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jToggleButtonBackward.setFocusable(false);
        jToggleButtonBackward.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jToggleButtonBackward.setMaximumSize(new java.awt.Dimension(52, 52));
        jToggleButtonBackward.setMinimumSize(new java.awt.Dimension(48, 48));
        jToggleButtonBackward.setName("Backward");
        jToggleButtonBackward.setPreferredSize(new java.awt.Dimension(52, 52));
        jToggleButtonBackward.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToggleButtonBackward.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toggleButtonPress(evt);
            }
        });

        jToggleButtonPlay.setFocusable(false);
        jToggleButtonPlay.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jToggleButtonPlay.setMaximumSize(new java.awt.Dimension(52, 52));
        jToggleButtonPlay.setMinimumSize(new java.awt.Dimension(48, 48));
        jToggleButtonPlay.setName("Play");
        jToggleButtonPlay.setPreferredSize(new java.awt.Dimension(52, 52));
        jToggleButtonPlay.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToggleButtonPlay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toggleButtonPress(evt);
            }
        });

        jToggleButtonStop.setFocusable(false);
        jToggleButtonStop.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jToggleButtonStop.setMaximumSize(new java.awt.Dimension(52, 52));
        jToggleButtonStop.setMinimumSize(new java.awt.Dimension(48, 48));
        jToggleButtonStop.setName("Stop");
        jToggleButtonStop.setPreferredSize(new java.awt.Dimension(52, 52));
        jToggleButtonStop.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToggleButtonStop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toggleButtonPress(evt);
            }
        });

        jToggleButtonForward.setFocusable(false);
        jToggleButtonForward.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jToggleButtonForward.setMaximumSize(new java.awt.Dimension(52, 52));
        jToggleButtonForward.setMinimumSize(new java.awt.Dimension(48, 48));
        jToggleButtonForward.setName("Forward");
        jToggleButtonForward.setPreferredSize(new java.awt.Dimension(52, 52));
        jToggleButtonForward.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToggleButtonForward.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toggleButtonPress(evt);
            }
        });

        jProgressBar1.setMaximumSize(new java.awt.Dimension(32767, 48));
        jProgressBar1.setMinimumSize(new java.awt.Dimension(10, 48));
        jProgressBar1.setPreferredSize(new java.awt.Dimension(146, 48));
        jProgressBar1.setString("");
        jProgressBar1.setStringPainted(true);
        jProgressBar1.setVerifyInputWhenFocusTarget(false);
        jProgressBar1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jProgressBar1MouseClicked(evt);
            }
        });

        jButtonSettings.setName("Settings");
        jButtonSettings.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toggleButtonPress(evt);
            }
        });

        jSplitPane1.setDividerLocation(256);

        jTablePlaylist.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jTablePlaylist.setGridColor(jTablePlaylist.getBackground());
        jTablePlaylist.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableRowClicked(evt);
            }
        });
        jScrollPanePlaylist.setViewportView(jTablePlaylist);

        jSplitPane1.setRightComponent(jScrollPanePlaylist);

        jLabelAlbumArt.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jLabelAlbumArt.setMaximumSize(new java.awt.Dimension(512, 512));
        jLabelAlbumArt.setMinimumSize(new java.awt.Dimension(100, 100));
        jLabelAlbumArt.setName("");
        jLabelAlbumArt.setPreferredSize(new java.awt.Dimension(256, 256));
        jLabelAlbumArt.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                jLabelAlbumArtComponentResized(evt);
            }
        });

        jListMediaMetadata.setName("");
        jScrollPaneMetadata.setViewportView(jListMediaMetadata);

        javax.swing.GroupLayout jPanelSidebarLayout = new javax.swing.GroupLayout(jPanelSidebar);
        jPanelSidebar.setLayout(jPanelSidebarLayout);
        jPanelSidebarLayout.setHorizontalGroup(
            jPanelSidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabelAlbumArt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPaneMetadata, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        jPanelSidebarLayout.setVerticalGroup(
            jPanelSidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelSidebarLayout.createSequentialGroup()
                .addComponent(jLabelAlbumArt, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPaneMetadata, javax.swing.GroupLayout.DEFAULT_SIZE, 242, Short.MAX_VALUE))
        );

        jSplitPane1.setLeftComponent(jPanelSidebar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToggleButtonBackward, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jToggleButtonPlay, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jToggleButtonStop, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jToggleButtonForward, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jProgressBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonSettings, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 806, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jProgressBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jToggleButtonBackward, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jToggleButtonPlay, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jToggleButtonStop, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jToggleButtonForward, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButtonSettings, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSplitPane1))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

	private void loadMusicFiles(File path){	
		jMusicController.getPlaylist().loadPlaylist(path);
		
		updatePlaylistModel(jMusicController.getPlaylist().getModel());
	}
	
	public void updatePlaylistModel(AbstractTableModel playlistModel){
		jTablePlaylist.setModel(playlistModel);
	}
	
	/**
	 * Loads the given image file and returns it as an image object.
	 * 
	 * @param file The image file
	 * @return The image object for the file to load, or null if there is a problem
	 */
	private Image getImage(File file){
		// If the file object isn't null, try loading it
		if (file != null){
			try {
				return ImageIO.read(file);
			} catch (IOException ex) {
				Logger.getLogger(jMusic.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		return null;
	}
	
	/**
	 * Play the selected row in the playlist.
	 * 
	 * If no rows are selected then the first row is selected (if there are any
	 * rows in the playlist).
	 * 
	 * 1. Tells the row to play.
	 * 2. Loads the image for the row and displays it as the album art.
	 * 3. Load the metadata into the details pane.
	 */
	private void playFile(){
		jToggleButtonPlay.setSelected(true);
		int selected = jTablePlaylist.getSelectedRow();
		if (selected == -1){
			jTablePlaylist.addRowSelectionInterval(0, 0);
			selected = jTablePlaylist.getSelectedRow();
		}
		
		Row row = ((MusicTableModel)jTablePlaylist.getModel()).getRow(selected);
		// Play music
		row.play();
		setAlbumArt(row.getImageFile());
		setMetadata(row.getMetadataArray());
	}
	
	/**
	 * Set the metadata list to the supplied string array.
	 * 
	 * @param data A string array of metadata details
	 */
	private void setMetadata(String[] data){
		if (data == null){
			// Clear the data
			jListMediaMetadata.setListData(new String[]{});
		} else {
			// Used the the supplied data
			jListMediaMetadata.setListData(data);
		}
	}
	
	/**
	 * Set the album art to the passed in file, or clear the image if empty.
	 * 
	 * @param image The image to load or null to clear the album art component
	 */
	private void setAlbumArt(File image){
		albumImage = getImage(image);
		resizeImageToFit();
	}
	
	/**
	 * Resize the current album art image to fit the album art control
	 */
	private void resizeImageToFit(){
		ImageIcon album = null;
		
		if (albumImage != null){
			BufferedImage bi = new BufferedImage(jLabelAlbumArt.getWidth(), jLabelAlbumArt.getHeight(), BufferedImage.TYPE_INT_ARGB);
			Graphics g = bi.createGraphics();
			g.drawImage(albumImage, 0, 0, jLabelAlbumArt.getWidth(), jLabelAlbumArt.getHeight(), null);
			album = new ImageIcon(bi);
		}
		
		jLabelAlbumArt.setIcon(album);
	}
	
	private void jTableRowClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableRowClicked
		if (evt.getClickCount() == 2){
			jMusicController.getMusicPlayer().stop();
			jToggleButtonPlay.setSelected(true);
			jToggleButtonStop.setSelected(false);
			
			//double click
			playFile();
		}
	}//GEN-LAST:event_jTableRowClicked

	private void toggleButtonPress(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_toggleButtonPress
		String buttonName = ((AbstractButton)evt.getSource()).getName();
		int currentRow = jTablePlaylist.getSelectedRow();
		switch (buttonName) {
			case "Play":
				MusicPlayer mp = jMusicController.getMusicPlayer();
				if (!mp.isPlaying()){
					// If we arn't playing, then play a file
					playFile();
					jToggleButtonPlay.setSelected(true);
					setPlayingIcon();
				} else if (mp.isPaused()){
					// If we are paused, then resume
					jMusicController.getMusicPlayer().resume();
					jToggleButtonPlay.setSelected(true);
					setPlayingIcon();
				} else {
					// If we aren't paused, then pause
					jMusicController.getMusicPlayer().pause();
					jToggleButtonPlay.setSelected(true);
					setPauseIcon();
				}
				break;
			case "Stop":
				jMusicController.getMusicPlayer().stop();
				jToggleButtonPlay.setSelected(false);
				jToggleButtonStop.setSelected(false);
				break;
			case "Forward":
				jMusicController.getMusicPlayer().stop();
				currentRow++;
				if (currentRow >= jTablePlaylist.getRowCount())
					currentRow = 0;
				jTablePlaylist.setRowSelectionInterval(currentRow, currentRow);
				jToggleButtonForward.setSelected(false);
				playFile();
				break;
			case "Backward":
				jMusicController.getMusicPlayer().stop();
				currentRow--;
				if (currentRow <= 0)
					currentRow = jTablePlaylist.getRowCount() - 1;
				jTablePlaylist.setRowSelectionInterval(currentRow, currentRow);
				jToggleButtonBackward.setSelected(false);
				playFile();
				break;
			case "Settings":
				Settings settings = new Settings();
				settings.setVisible(true);
				break;
			default:
				System.out.println("Unknown button: " + buttonName);
				break;
		}
	}//GEN-LAST:event_toggleButtonPress

	private void jProgressBar1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jProgressBar1MouseClicked
		float position = evt.getX() / (float)evt.getComponent().getWidth();
		long positionMicroseconds = (long)(jMusicController.getMusicPlayer().getSongLength() * position);
		jMusicController.getMusicPlayer().setSongPosition(positionMicroseconds);
		System.out.println("Position: " + position);
	}//GEN-LAST:event_jProgressBar1MouseClicked

	private void jLabelAlbumArtComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jLabelAlbumArtComponentResized
		Rectangle parentRect = jLabelAlbumArt.getParent().getBounds();
		
		Rectangle albumArtRect = jLabelAlbumArt.getBounds();
		albumArtRect.height = albumArtRect.width;
		jLabelAlbumArt.setBounds(albumArtRect);
		
		Rectangle metadataRect = jScrollPaneMetadata.getBounds();
		metadataRect.y = albumArtRect.height + 6;
		metadataRect.height = parentRect.height - albumArtRect.height - 6;
		jScrollPaneMetadata.setBounds(metadataRect);
		
		resizeImageToFit();
	}//GEN-LAST:event_jLabelAlbumArtComponentResized

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonSettings;
    private javax.swing.JLabel jLabelAlbumArt;
    private javax.swing.JList jListMediaMetadata;
    private javax.swing.JPanel jPanelSidebar;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JScrollPane jScrollPaneMetadata;
    private javax.swing.JScrollPane jScrollPanePlaylist;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JTable jTablePlaylist;
    private javax.swing.JToggleButton jToggleButtonBackward;
    private javax.swing.JToggleButton jToggleButtonForward;
    private javax.swing.JToggleButton jToggleButtonPlay;
    private javax.swing.JToggleButton jToggleButtonStop;
    // End of variables declaration//GEN-END:variables

}
