package com.nxxr.spaces.ui.screens.booking

import android.app.TimePickerDialog
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nxxr.spaces.R
import com.nxxr.spaces.data.model.Seat
import com.nxxr.spaces.ui.theme.Accent
import com.nxxr.spaces.ui.theme.OnSurface
import com.nxxr.spaces.ui.theme.Primary
import com.nxxr.spaces.ui.theme.Tint
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter


@Composable
fun SeatCard(
    seat: Seat,
    onClick: () -> Unit
) {
    val backgroundColor = if (seat.booked) MaterialTheme.colorScheme.surfaceVariant else MaterialTheme.colorScheme.surface
    val textColor = if (seat.booked) MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f) else MaterialTheme.colorScheme.onSurface

    Card(
        modifier = Modifier
            .size(60.dp)
            .padding(4.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor)
        ) {
            Text(
                text = seat.number.toString(),
                color = textColor,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}




@Composable
fun SeatLayout(
    seats: List<Seat>,
    onClick: (Seat) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Top section (2 blocks of 4x2)
        repeat(4) { row ->
            Row(horizontalArrangement = Arrangement.Center) {
                repeat(2) { col ->
                    SeatCard(seat = seats[row * 4 + col], onClick = { onClick(seats[row * 4 + col]) })
                }
                Spacer(modifier = Modifier.width(36.dp)) // aisle
                repeat(2) { col ->
                    SeatCard(seat = seats[row * 4 + 2 + col], onClick = { onClick(seats[row * 4 + 2 + col]) })
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Middle section (2 blocks of 3x2)
        repeat(3) { row ->
            Row(horizontalArrangement = Arrangement.Center) {
                repeat(2) { col ->
                    val index = 16 + row * 4 + col
                    SeatCard(seat = seats[index], onClick = { onClick(seats[index]) })
                }
                Spacer(modifier = Modifier.width(16.dp))
                repeat(2) { col ->
                    val index = 16 + row * 4 + 2 + col
                    SeatCard(seat = seats[index], onClick = { onClick(seats[index]) })
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Bottom section (1 block of 2x5)
        repeat(2) { row ->
            Row(horizontalArrangement = Arrangement.Center) {
                repeat(5) { col ->
                    val index = 28 + row * 5 + col
                    SeatCard(seat = seats[index], onClick = { onClick(seats[index]) })
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SeatsGridScreen(viewModel: SeatsViewModel) {
    val seats by viewModel.seats.collectAsState()

    val context = LocalContext.current
    val bookingMessage by viewModel.bookingMessage.collectAsState()

    LaunchedEffect(bookingMessage) {
        bookingMessage?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            viewModel.resetBookingMessage()
        }
    }

    var selectedSeat by remember { mutableStateOf<Seat?>(null) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    Scaffold {
        Box(Modifier.padding(it),
            contentAlignment = Alignment.Center) {
            if (seats.size < 38) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                SeatLayout(seats = seats, onClick = { seat ->
                    if (!seat.booked) selectedSeat = seat
                })
            }

            selectedSeat?.let { seat ->
                ModalBottomSheet(
                    onDismissRequest = { selectedSeat = null },
                    sheetState = sheetState
                ) {
                    SeatBookingBottomSheet(
                        seat = seat,
                        onConfirmBooking = { startMillis, endMillis ->
                            viewModel.bookSeat(seat, startMillis, endMillis)
                            selectedSeat = null // dismiss sheet after booking
                        },
                        onDismiss = {
                            selectedSeat = null // dismiss sheet on cancel
                        }
                    )
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SeatBookingBottomSheet(
    seat: Seat,
    onConfirmBooking: (Long, Long) -> Unit,
    onDismiss: () -> Unit
) {
    val now = remember { LocalTime.now() }
    var startTime by remember { mutableStateOf(now.plusMinutes(30)) }
    var endTime by remember { mutableStateOf(startTime.plusMinutes(30)) }

    val today = LocalDate.now()
    val zoneId = ZoneId.systemDefault()
    val formatter = DateTimeFormatter.ofPattern("hh:mm a")

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(MaterialTheme.colorScheme.surface, shape = RoundedCornerShape(24.dp))
            .clip(RoundedCornerShape(24.dp))
    ) {
        // Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = "Booking Invoice",
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = "Choose your check-in and out times",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            }
        }

        HorizontalDivider()

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .clip(RoundedCornerShape(24.dp))
                .background(MaterialTheme.colorScheme.secondaryContainer)
                .padding(20.dp)
            ,


        ) {

            Text(
                text = "Seat ${seat.number}",
                style = MaterialTheme.typography.titleLarge
            )
            HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))
            Row {
                Icon(
                    painter = painterResource(id = R.drawable.check_in),
                    contentDescription = "Check In Time",
                    modifier = Modifier.size(28.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text("Check In Time", style = MaterialTheme.typography.titleLarge)
                    TimeSelector(
                        time = startTime,
                        label = "Start Time"
                    ) { newStart ->
                        if (newStart.isAfter(now)) {
                            startTime = newStart
                            if (endTime.isBefore(newStart.plusMinutes(30))) {
                                endTime = newStart.plusMinutes(30)
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Row {
                Icon(
                    painter = painterResource(id = R.drawable.check_out),
                    contentDescription = "Check Out Time",
                    modifier = Modifier.size(28.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {

                    Text("Check Out Time", style = MaterialTheme.typography.titleLarge)
                    Spacer(Modifier.height(8.dp))
                    TimeSelector(
                        time = endTime,
                        label = "End Time"
                    ) { newEnd ->
                        if (newEnd.isAfter(startTime)) {
                            endTime = newEnd
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.End
        ) {
            TextButton(
                onClick = onDismiss,
                colors = ButtonDefaults.textButtonColors( contentColor = MaterialTheme.colorScheme.error)
                ) {
                Text("Cancel")
            }

            Spacer(modifier = Modifier.width(8.dp))

            Button(
                onClick = {
                    val startMillis = startTime.atDate(today).atZone(zoneId).toInstant().toEpochMilli()
                    val endMillis = endTime.atDate(today).atZone(zoneId).toInstant().toEpochMilli()
                    onConfirmBooking(startMillis, endMillis)
                },
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary, contentColor = MaterialTheme.colorScheme.onPrimary),
                enabled = endTime.isAfter(startTime)
            ) {
                Text("Confirm")
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TimeSelector(
    label: String,
    time: LocalTime,
    onTimeChange: (LocalTime) -> Unit
) {
    val context = LocalContext.current
    val hour = time.hour
    val minute = time.minute

    val formatter = DateTimeFormatter.ofPattern("hh:mm a")

    val timePickerDialog = remember {
        TimePickerDialog(
            context,
            { _, selectedHour, selectedMinute ->
                val newTime = LocalTime.of(selectedHour, selectedMinute)
                onTimeChange(newTime)
            },
            hour,
            minute,
            false // 12-hour format
        )
    }

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = time.format(formatter),
            style = MaterialTheme.typography.bodyLarge
        )

        IconButton(onClick = { timePickerDialog.show() }) {
            Icon(
                painter = painterResource(id = R.drawable.schedule),
                contentDescription = "Select Time"
            )
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun PreviewSeatBookingBottomSheet() {
    SeatBookingBottomSheet(
        seat = Seat(1.toString(), 1, false),
        onConfirmBooking = { _, _ -> },
        onDismiss = {}
    )
}