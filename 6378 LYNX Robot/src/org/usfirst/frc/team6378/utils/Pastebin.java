//
///* CHANGING SPEEDS */
//		if (m_xBox.getYButton())
//			m_driveSpeed = DRIVE_SPEED_FAST;
//		else if (m_xBox.getXButton())
//			m_driveSpeed = DRIVE_SPEED_SLOW;
//
//		/* DRIVING */
//
//		// -y is forward, +y is backward
//
//		// // Xbox controller with triggers
//		double leftTrigger = m_xBox.getRawAxis(Mapping.l_trigger_axis);
//		double rightTrigger = m_xBox.getRawAxis(Mapping.r_trigger_axis);
//
//		double y = 0;
//		double x = -m_xBox.getRawAxis(4);
//
//		if (rightTrigger > 0)
//			y = -rightTrigger;
//		else if (leftTrigger > 0)
//			y = leftTrigger;
//
//		y = Utils.map(y, -1, 1, -m_driveSpeed, m_driveSpeed);
//		x = Utils.map(x, -1, 1, -m_driveSpeed, m_driveSpeed);
//
//		SmartDashboard.putString("DB/String 2", "Distance: " + encoder.getDistance());