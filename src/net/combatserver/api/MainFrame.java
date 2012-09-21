package net.combatserver.api;

import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.UIManager;

import net.combatserver.protobuf.DataStructures.Room;
import net.combatserver.protobuf.DataStructures.User;
import net.combatserver.protobuf.DataStructures.Region;


public class MainFrame extends javax.swing.JFrame
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Connection con;
    private RoomListModel roomListModel;
    private UserListModel userListModel;
    private StringBuilder chatHistory;
	private String host="localhost";
	private int port=6789;

    /** Creates new form MainFrame */
    public MainFrame()
    {
        //Sets the native look and feel
        try
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        
        initComponents();
        //URL iconURL = getClass().getClassLoader().getResource("resources/redFox.gif");
//        if(iconURL != null)
//        {
//            ImageIcon icon = new ImageIcon(iconURL);
//            setIconImage(icon.getImage());
//        }
        
        con = new Connection(host,port);
        con.addEventListener(EventHandler.onNewUser, this);
        con.addEventListener(EventHandler.onNewRoom, this);
        con.addEventListener(EventHandler.onJoinRoom, this);
        con.addEventListener(EventHandler.onRoomList, this);
        con.addEventListener(EventHandler.onUserList, this);
        con.addEventListener(EventHandler.onRegionInfo, this);
        con.addEventListener(EventHandler.onConnected, this);
        con.addEventListener(EventHandler.onUserLeave, this);
        con.addEventListener(EventHandler.onCreateRoom, this); 
        con.addEventListener(EventHandler.onChangeRoom, this);        
        con.addEventListener(EventHandler.onJoinServer, this);
        con.addEventListener(EventHandler.onNewMessage, this);
        con.addEventListener(EventHandler.onRoomRemove, this);
        con.addEventListener(EventHandler.onServerError, this);
        
        //Sets the Room and User list models to the user and room lists
        this.roomListModel = (RoomListModel)listRooms.getModel();
        this.userListModel = (UserListModel)listUsers.getModel();

    }
    public void onConnected(int delay){
		System.out.println("onConnected");
		System.out.println(delay);
        dialogLogin.pack();
        textUserName.setText("AW7pxSWfy1NI1i4BHgcy");
        textPassWord.setText("9Nw7Ke9kREe");
        dialogLogin.setLocation(getX() + 100, getY() + 100);
        dialogLogin.setVisible(true);
        buttonLogout.setEnabled(true);
    }

    public void onJoinServer(User user){
    	System.out.println("onJoinServer");
        chatHistory = new StringBuilder();
        textChatHistory.setText("");
        labelCopyright.setText("User :"+((User)user).getName());
        con.joinDefaultRoom();
    }  
    public void onJoinRoom(Room room){
    	System.out.println("onJoinRoom");
    	con.getRegionRequest(Connection.DefaultRegion);
    }
    public void onUserList(List<User> users){
    	System.out.println("onUserList");
    	userListModel.setUserList(Connection.userList);
    }
    public void onRegionInfo(Region region){
    	System.out.println("onRegionInfo");
    	buttonNewRoom.setEnabled(true);
    }
    public void onRoomList(List<Room> rooms){
    	System.out.println("onRoomList");
    	roomListModel.setRoomList(Connection.roomList);
    }    
    public void onNewUser(User user){
    	System.out.println("onNewUser");
    	userListModel.addUser(user);
    }
    public void onUserLeave(int userid){
    	System.out.println("onUserLeave");
    	userListModel.removeUser(userid);
    }
    public void onChangeRoom(Room room){
    	System.out.println("onChangeRoom");
        listRooms.setSelectedValue(room, true);
        buttonJoin.setEnabled(false);
        textChatMessage.setEnabled(true);
        buttonSend.setEnabled(true);
        buttonNewRoom.setEnabled(true);
        chatHistory.append("<font color='#cc0000'>{ Room <b>");
        chatHistory.append(room.getName());
        chatHistory.append("</b> joined }</font><br>");
        textChatHistory.setText(chatHistory.toString());
        textChatMessage.requestFocus();
    }    
    public void onNewMessage(int router,String message,String username,int userid){
    	System.out.println("onNewMessage");
    	if (router==-1){
    		chatHistory.append("[私聊]");
    	}else if (router==-2){
    		chatHistory.append("[房间]");
    	}else if (router==-3){
    		chatHistory.append("[地区]");
    	}else if (router==-4){
    		chatHistory.append("[公告]");
    	}
        chatHistory.append("<b>[");
        chatHistory.append(username);
        chatHistory.append("]:</b> ");
        chatHistory.append(message);
        chatHistory.append("<br>");
        textChatHistory.setText(chatHistory.toString());
    }
    public void onNewRoom(Room room){
    	System.out.println("onNewRoom");
    	roomListModel.addRoom(room);
    }
    public void onCreateRoom(Room room){
    	System.out.println("onCreateRoom");
    	roomListModel.addRoom(room);	
    }
    public void onRoomRemove(int roomid){
    	System.out.println("onRoomRemove");
    	Room room=Connection.roomList.get(roomid);
    	roomListModel.removeRoom(room);
    }
    
 

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        dialogNewRoom = new javax.swing.JDialog();
        labelRoomName = new javax.swing.JLabel();
        textNewRoomName = new javax.swing.JTextField();
        labelMaxUsers = new javax.swing.JLabel();
        labelPassword = new javax.swing.JLabel();
        textNewRoomPassword = new javax.swing.JPasswordField();
        buttonCreateRoomOk = new javax.swing.JButton();
        buttonCreateRoomCancel = new javax.swing.JButton();
        sliderNewRoomMaxUsers = new javax.swing.JSlider();
        dialogPrivate = new javax.swing.JDialog();
        labelPrivateMessage = new javax.swing.JLabel();
        textPrivateMessage = new javax.swing.JTextField();
        buttonSendPrivateOk = new javax.swing.JButton();
        buttonSendPrivateCancel = new javax.swing.JButton();
        dialogJoinPrivateRoom = new javax.swing.JDialog();
        labelPrivateRoomPassword = new javax.swing.JLabel();
        textJoinPrivatePassword = new javax.swing.JPasswordField();
        buttonJoinPrivateCancel = new javax.swing.JButton();
        buttonJoinPrivateOk = new javax.swing.JButton();
        dialogLogin = new javax.swing.JDialog();
        labelUserName = new javax.swing.JLabel();
        textUserName = new javax.swing.JTextField();
        textPassWord = new javax.swing.JTextField();
        buttonLogin = new javax.swing.JButton();
        dialogAlert = new javax.swing.JDialog();
        labelAlert = new javax.swing.JLabel();
        buttonAlertOk = new javax.swing.JButton();
        labelChatHistory = new javax.swing.JLabel();
        scrollPaneChatHistory = new javax.swing.JScrollPane();
        textChatHistory = new javax.swing.JTextPane();
        textChatMessage = new javax.swing.JTextField();
        buttonSend = new javax.swing.JButton();
        scrollPaneRoomList = new javax.swing.JScrollPane();
        listRooms = new javax.swing.JList();
        labelRoomList = new javax.swing.JLabel();
        buttonJoin = new javax.swing.JButton();
        buttonNewRoom = new javax.swing.JButton();
        scrollPaneUserList = new javax.swing.JScrollPane();
        listUsers = new javax.swing.JList();
        buttonSendPrivate = new javax.swing.JButton();
        labelUserList = new javax.swing.JLabel();
        buttonLogout = new javax.swing.JButton();
        labelCopyright = new javax.swing.JLabel();

        dialogNewRoom.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        dialogNewRoom.setTitle("Create New Room");
        dialogNewRoom.setModal(true);
        dialogNewRoom.setName(null);
        dialogNewRoom.setResizable(false);

        labelRoomName.setText("Room name:");

        labelMaxUsers.setText("Max. users:");

        labelPassword.setText("Password (optional):");

        buttonCreateRoomOk.setText("Create");
        buttonCreateRoomOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCreateRoomOkActionPerformed(evt);
            }
        });

        buttonCreateRoomCancel.setText("Cancel");
        buttonCreateRoomCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCreateRoomCancelActionPerformed(evt);
            }
        });

        sliderNewRoomMaxUsers.setMajorTickSpacing(10);
        sliderNewRoomMaxUsers.setMaximum(50);
        sliderNewRoomMaxUsers.setMinimum(10);
        sliderNewRoomMaxUsers.setMinorTickSpacing(10);
        sliderNewRoomMaxUsers.setPaintLabels(true);
        sliderNewRoomMaxUsers.setPaintTicks(true);
        sliderNewRoomMaxUsers.setSnapToTicks(true);

        javax.swing.GroupLayout dialogNewRoomLayout = new javax.swing.GroupLayout(dialogNewRoom.getContentPane());
        dialogNewRoom.getContentPane().setLayout(dialogNewRoomLayout);
        dialogNewRoomLayout.setHorizontalGroup(
            dialogNewRoomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dialogNewRoomLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(dialogNewRoomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(dialogNewRoomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(textNewRoomName, javax.swing.GroupLayout.DEFAULT_SIZE, 260, Short.MAX_VALUE)
                        .addComponent(labelRoomName)
                        .addComponent(labelMaxUsers)
                        .addComponent(sliderNewRoomMaxUsers, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(labelPassword)
                        .addComponent(textNewRoomPassword))
                    .addGroup(dialogNewRoomLayout.createSequentialGroup()
                        .addComponent(buttonCreateRoomOk)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buttonCreateRoomCancel)))
                .addContainerGap(26, Short.MAX_VALUE))
        );
        dialogNewRoomLayout.setVerticalGroup(
            dialogNewRoomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dialogNewRoomLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelRoomName)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(textNewRoomName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(labelMaxUsers)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sliderNewRoomMaxUsers, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(labelPassword)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(textNewRoomPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(dialogNewRoomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonCreateRoomOk)
                    .addComponent(buttonCreateRoomCancel))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        dialogPrivate.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        dialogPrivate.setTitle("Send Private Message");
        dialogPrivate.setModal(true);
        dialogPrivate.setName(null);
        dialogPrivate.setResizable(false);

        labelPrivateMessage.setText("Message:");

        buttonSendPrivateOk.setText("Send");
        buttonSendPrivateOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonSendPrivateOkActionPerformed(evt);
            }
        });

        buttonSendPrivateCancel.setText("Cancel");
        buttonSendPrivateCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonSendPrivateCancelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout dialogPrivateLayout = new javax.swing.GroupLayout(dialogPrivate.getContentPane());
        dialogPrivate.getContentPane().setLayout(dialogPrivateLayout);
        dialogPrivateLayout.setHorizontalGroup(
            dialogPrivateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dialogPrivateLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(dialogPrivateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(textPrivateMessage, javax.swing.GroupLayout.DEFAULT_SIZE, 374, Short.MAX_VALUE)
                    .addComponent(labelPrivateMessage)
                    .addGroup(dialogPrivateLayout.createSequentialGroup()
                        .addComponent(buttonSendPrivateOk)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buttonSendPrivateCancel)))
                .addContainerGap())
        );
        dialogPrivateLayout.setVerticalGroup(
            dialogPrivateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dialogPrivateLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelPrivateMessage)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(textPrivateMessage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(dialogPrivateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonSendPrivateOk)
                    .addComponent(buttonSendPrivateCancel))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        dialogJoinPrivateRoom.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        dialogJoinPrivateRoom.setTitle("Join Private Room");
        dialogJoinPrivateRoom.setModal(true);
        dialogJoinPrivateRoom.setName(null);
        dialogJoinPrivateRoom.setResizable(false);

        labelPrivateRoomPassword.setText("Password:");

        buttonJoinPrivateCancel.setText("Cancel");
        buttonJoinPrivateCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonJoinPrivateCancelActionPerformed(evt);
            }
        });

        buttonJoinPrivateOk.setText("Join");
        buttonJoinPrivateOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonJoinPrivateOkActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout dialogJoinPrivateRoomLayout = new javax.swing.GroupLayout(dialogJoinPrivateRoom.getContentPane());
        dialogJoinPrivateRoom.getContentPane().setLayout(dialogJoinPrivateRoomLayout);
        dialogJoinPrivateRoomLayout.setHorizontalGroup(
            dialogJoinPrivateRoomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dialogJoinPrivateRoomLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(dialogJoinPrivateRoomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(textJoinPrivatePassword, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelPrivateRoomPassword)
                    .addGroup(dialogJoinPrivateRoomLayout.createSequentialGroup()
                        .addComponent(buttonJoinPrivateOk)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buttonJoinPrivateCancel)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        dialogJoinPrivateRoomLayout.setVerticalGroup(
            dialogJoinPrivateRoomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dialogJoinPrivateRoomLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelPrivateRoomPassword)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(textJoinPrivatePassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(dialogJoinPrivateRoomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonJoinPrivateOk)
                    .addComponent(buttonJoinPrivateCancel))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        dialogLogin.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        dialogLogin.setTitle("Login");
        dialogLogin.setModal(true);
        dialogLogin.setName(null);
        dialogLogin.setResizable(false);

        labelUserName.setText("Type your user name:");

        buttonLogin.setText("Login");
        buttonLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonLoginActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout dialogLoginLayout = new javax.swing.GroupLayout(dialogLogin.getContentPane());
        dialogLogin.getContentPane().setLayout(dialogLoginLayout);
        dialogLoginLayout.setHorizontalGroup(
            dialogLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dialogLoginLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(dialogLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(textUserName, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textPassWord, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelUserName)
                    .addComponent(buttonLogin))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        dialogLoginLayout.setVerticalGroup(
            dialogLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dialogLoginLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelUserName)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(textUserName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(textPassWord, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(buttonLogin)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        dialogAlert.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        dialogAlert.setTitle("Alert");
        dialogAlert.setModal(true);
        dialogAlert.setResizable(false);

        labelAlert.setText("The alert message goes here...");

        buttonAlertOk.setText("Ok");
        buttonAlertOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonAlertOkActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout dialogAlertLayout = new javax.swing.GroupLayout(dialogAlert.getContentPane());
        dialogAlert.getContentPane().setLayout(dialogAlertLayout);
        dialogAlertLayout.setHorizontalGroup(
            dialogAlertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dialogAlertLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(dialogAlertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelAlert, javax.swing.GroupLayout.DEFAULT_SIZE, 309, Short.MAX_VALUE)
                    .addComponent(buttonAlertOk, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        dialogAlertLayout.setVerticalGroup(
            dialogAlertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dialogAlertLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelAlert)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addComponent(buttonAlertOk)
                .addContainerGap())
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("SmartFoxJChat");
        setLocationByPlatform(true);
        setMinimumSize(new java.awt.Dimension(660, 650));
        setName("frmMain"); // NOI18N
        setResizable(false);

        labelChatHistory.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        labelChatHistory.setText("Chat History");
        labelChatHistory.setName("labelChatHistory"); // NOI18N

        textChatHistory.setContentType("text/html");
        textChatHistory.setEditable(false);
        textChatHistory.setName("textChatHistory"); // NOI18N
        scrollPaneChatHistory.setViewportView(textChatHistory);

        textChatMessage.setEnabled(false);
        textChatMessage.setName("textChatMessage"); // NOI18N
        textChatMessage.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                textChatMessageKeyPressed(evt);
            }
        });

        buttonSend.setText("Send");
        buttonSend.setEnabled(false);
        buttonSend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonSendActionPerformed(evt);
            }
        });

        listRooms.setModel(new RoomListModel());
        listRooms.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        //listRooms.setCellRenderer(new RoomListCellRenderer());
        listRooms.setName("listRooms"); // NOI18N
        listRooms.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                listRoomsValueChanged(evt);
            }
        });
        scrollPaneRoomList.setViewportView(listRooms);

        labelRoomList.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        labelRoomList.setText("Room List");

        buttonJoin.setText("Join");
        buttonJoin.setEnabled(false);
        buttonJoin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonJoinActionPerformed(evt);
            }
        });

        buttonNewRoom.setText("New Room");
        buttonNewRoom.setEnabled(false);
        buttonNewRoom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonNewRoomActionPerformed(evt);
            }
        });

        listUsers.setModel(new UserListModel());
        listUsers.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        //listUsers.setCellRenderer(new UserListCellRenderer());
        listUsers.setVisibleRowCount(20);
        listUsers.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                listUsersValueChanged(evt);
            }
        });
        scrollPaneUserList.setViewportView(listUsers);

        buttonSendPrivate.setText("Send Private Message");
        buttonSendPrivate.setEnabled(false);
        buttonSendPrivate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonSendPrivateActionPerformed(evt);
            }
        });

        labelUserList.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        labelUserList.setText("User List");

        buttonLogout.setText("Logout");
        buttonLogout.setEnabled(false);
        buttonLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonLogoutActionPerformed(evt);
            }
        });

        labelCopyright.setText("");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(scrollPaneUserList, 0, 0, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(buttonJoin)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(buttonNewRoom))
                            .addComponent(labelUserList)
                            .addComponent(labelRoomList)
                            .addComponent(scrollPaneRoomList, javax.swing.GroupLayout.DEFAULT_SIZE, 175, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelChatHistory, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(scrollPaneChatHistory, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 459, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(buttonSendPrivate, javax.swing.GroupLayout.DEFAULT_SIZE, 175, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textChatMessage, javax.swing.GroupLayout.DEFAULT_SIZE, 325, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buttonSend, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buttonLogout))
                    .addComponent(labelCopyright))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelRoomList)
                    .addComponent(labelChatHistory))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(scrollPaneRoomList, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(buttonJoin)
                            .addComponent(buttonNewRoom))
                        .addGap(18, 18, 18)
                        .addComponent(labelUserList)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(scrollPaneUserList, javax.swing.GroupLayout.PREFERRED_SIZE, 304, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(scrollPaneChatHistory))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonSendPrivate)
                    .addComponent(textChatMessage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonSend)
                    .addComponent(buttonLogout))
                .addGap(18, 18, 18)
                .addComponent(labelCopyright)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

/**
 * When the user slects new room enables "Join Room" button if the room
 * is not the current, otherwise disables it.
 */
private void listRoomsValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_listRoomsValueChanged
    Room room = (Room)listRooms.getSelectedValue();
    if(room != null && room.getId() != con.activeRoomId)
    {
        buttonJoin.setEnabled(true);
    }
    else
    {
        buttonJoin.setEnabled(false);
    }
}//GEN-LAST:event_listRoomsValueChanged

/**
 * Joins the user to the selected room.
 * If the room is private a dilog box that asks for the password is shown.
 */
private void buttonJoinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonJoinActionPerformed
    Room room = (Room)listRooms.getSelectedValue();
    if(room.getNeedPassword())
    {
        dialogJoinPrivateRoom.pack();
        dialogJoinPrivateRoom.setLocation(getX() + 100, getY() + 100);
        textJoinPrivatePassword.setText("");
        dialogJoinPrivateRoom.setVisible(true);
    }
    else
    {
        con.changeRoom(room.getId());
    }
}//GEN-LAST:event_buttonJoinActionPerformed

/**
 * Sends public message to the server.
 */
private void buttonSendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonSendActionPerformed
    if(textChatMessage.getText().length() > 0)
    {
        con.sendMessage(-2,textChatMessage.getText());
        textChatMessage.setText("");
        textChatMessage.requestFocus();
    }
}//GEN-LAST:event_buttonSendActionPerformed

/**
 * When the user slects new user enables "Send Private Message" button if
 * the user is not the current user, otherwise disables it.
 */
private void listUsersValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_listUsersValueChanged
    User user = (User)listUsers.getSelectedValue();
//    if(user != null && user.getId() != sfs.myUserId)
//    {
//        buttonSendPrivate.setEnabled(true);
//    }
//    else
//    {
//        buttonSendPrivate.setEnabled(false);
//    }
}//GEN-LAST:event_listUsersValueChanged

/**
 * Displays a dialog that allows new room creation.
 */
private void buttonNewRoomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonNewRoomActionPerformed
    dialogNewRoom.pack();
    dialogNewRoom.setLocation(getX() + 100, getY() + 100);
    textNewRoomName.setText("");
    textNewRoomPassword.setText("");
    sliderNewRoomMaxUsers.setValue(50);
    dialogNewRoom.setVisible(true);
}//GEN-LAST:event_buttonNewRoomActionPerformed

/**
 * Shows dialog that asks for the private message to be send.
 */
private void buttonSendPrivateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonSendPrivateActionPerformed
    dialogPrivate.pack();
    dialogPrivate.setLocation(getX() + 100, getY() + 100);
    textChatMessage.setText("");
    dialogPrivate.setVisible(true);
}//GEN-LAST:event_buttonSendPrivateActionPerformed

/**
 * Sends private message to the server.
 */
private void buttonSendPrivateOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonSendPrivateOkActionPerformed
    String msg = textPrivateMessage.getText();
//    if(msg.length() > 0)
//    {
//        dialogPrivate.dispose();
//        User recipient = (User)listUsers.getSelectedValue();
//        if(recipient != null)
//        {
//            sfs.sendPrivateMessage(msg, recipient.getId());
//        }
//    }
}//GEN-LAST:event_buttonSendPrivateOkActionPerformed

/**
 * Closes the private message dialog box.
 */
private void buttonSendPrivateCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonSendPrivateCancelActionPerformed
    dialogPrivate.dispose();
}//GEN-LAST:event_buttonSendPrivateCancelActionPerformed

/**
 * Closes the new room dialog box.
 */
private void buttonCreateRoomCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCreateRoomCancelActionPerformed
    dialogNewRoom.dispose();
}//GEN-LAST:event_buttonCreateRoomCancelActionPerformed

/**
 * Creates new room
 */
private void buttonCreateRoomOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCreateRoomOkActionPerformed
    String roomName = textNewRoomName.getText();
    if(roomName.length() > 0)
    {
        dialogNewRoom.dispose();
//        //Gets the new room properties for the user input
//        //and sends them to the server.
        //int maxUsers = sliderNewRoomMaxUsers.getValue();
        String password = new String(textNewRoomPassword.getPassword());
       // Map<String, Object> roomProperties = new HashMap<String, Object>();
        //roomProperties.put("password", password);
        con.createRoom(2,roomName, password);
    }
}//GEN-LAST:event_buttonCreateRoomOkActionPerformed

/**
 * Closes the dialog box that allows the user to join to private room
 */
private void buttonJoinPrivateCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonJoinPrivateCancelActionPerformed
    dialogJoinPrivateRoom.dispose();
}//GEN-LAST:event_buttonJoinPrivateCancelActionPerformed

/**
 * Joins the user to private room
 */
private void buttonJoinPrivateOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonJoinPrivateOkActionPerformed
	dialogJoinPrivateRoom.dispose();
	Room room = (Room)listRooms.getSelectedValue();
	con.changeRoom(room.getId());
    //if(textJoinPrivatePassword.getPassword().length > 0)
  //  {
//        dialogJoinPrivateRoom.dispose();
//        //Gets the room password
//        String password = new String(textJoinPrivatePassword.getPassword());
//        //Joins the user to the currently selected room
//        Room room = (Room)listRooms.getSelectedValue();
//        sfs.joinRoom(room.getId(), password, false);
  //  }
}//GEN-LAST:event_buttonJoinPrivateOkActionPerformed

/**
 * Logs the user in.
 */
private void buttonLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonLoginActionPerformed
    if(textUserName.getText().length() > 0)
    {
        dialogLogin.dispose();
        con.loginServer(textUserName.getText(), textPassWord.getText());
    }
}//GEN-LAST:event_buttonLoginActionPerformed

/**
 * Closes the dialog box with the error messages.
 */
private void buttonAlertOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonAlertOkActionPerformed
    dialogAlert.dispose();
}//GEN-LAST:event_buttonAlertOkActionPerformed

/**
 * Sends a public message to the server.
 */
private void textChatMessageKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textChatMessageKeyPressed
    if(evt.getKeyCode() == KeyEvent.VK_ENTER)
    {
        buttonSendActionPerformed(null);
    }
}//GEN-LAST:event_textChatMessageKeyPressed

/**
 * Disconnects the user.
 */
private void buttonLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonLogoutActionPerformed
   // sfs.disconnect();
}//GEN-LAST:event_buttonLogoutActionPerformed

    public static void main(String args[])
    {
        //SmartFoxClient s = new SmartFoxClient(true);
        java.awt.EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                new MainFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonAlertOk;
    private javax.swing.JButton buttonCreateRoomCancel;
    private javax.swing.JButton buttonCreateRoomOk;
    private javax.swing.JButton buttonJoin;
    private javax.swing.JButton buttonJoinPrivateCancel;
    private javax.swing.JButton buttonJoinPrivateOk;
    private javax.swing.JButton buttonLogin;
    private javax.swing.JButton buttonLogout;
    private javax.swing.JButton buttonNewRoom;
    private javax.swing.JButton buttonSend;
    private javax.swing.JButton buttonSendPrivate;
    private javax.swing.JButton buttonSendPrivateCancel;
    private javax.swing.JButton buttonSendPrivateOk;
    private javax.swing.JDialog dialogAlert;
    private javax.swing.JDialog dialogJoinPrivateRoom;
    private javax.swing.JDialog dialogLogin;
    private javax.swing.JDialog dialogNewRoom;
    private javax.swing.JDialog dialogPrivate;
    private javax.swing.JLabel labelAlert;
    private javax.swing.JLabel labelChatHistory;
    private javax.swing.JLabel labelCopyright;
    private javax.swing.JLabel labelMaxUsers;
    private javax.swing.JLabel labelPassword;
    private javax.swing.JLabel labelPrivateMessage;
    private javax.swing.JLabel labelPrivateRoomPassword;
    private javax.swing.JLabel labelRoomList;
    private javax.swing.JLabel labelRoomName;
    private javax.swing.JLabel labelUserList;
    private javax.swing.JLabel labelUserName;
    @SuppressWarnings("rawtypes")
	private javax.swing.JList listRooms;
    @SuppressWarnings("rawtypes")
    private javax.swing.JList listUsers;
    private javax.swing.JScrollPane scrollPaneChatHistory;
    private javax.swing.JScrollPane scrollPaneRoomList;
    private javax.swing.JScrollPane scrollPaneUserList;
    private javax.swing.JSlider sliderNewRoomMaxUsers;
    private javax.swing.JTextPane textChatHistory;
    private javax.swing.JTextField textChatMessage;
    private javax.swing.JPasswordField textJoinPrivatePassword;
    private javax.swing.JTextField textNewRoomName;
    private javax.swing.JPasswordField textNewRoomPassword;
    private javax.swing.JTextField textPrivateMessage;
    private javax.swing.JTextField textUserName;
    private javax.swing.JTextField textPassWord;    
    // End of variables declaration//GEN-END:variables
}
