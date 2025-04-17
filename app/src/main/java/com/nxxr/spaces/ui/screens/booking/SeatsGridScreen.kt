package com.nxxr.spaces.ui.screens.booking

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nxxr.spaces.R
import com.nxxr.spaces.data.model.Seat


@Composable
fun SeatCard(
    seat: Seat,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .size(60.dp)
            .padding(4.dp)

            .clickable { onClick() },
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(if (seat.isBooked) colorResource(R.color.purple_700) else Color.White)
        ) {
            Text(
                text = seat.number.toString(),
                color = if( seat.isBooked) Color.LightGray else Color.Black,
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

@Composable
fun SeatsGridScreen(
    viewModel: SeatsViewModel,
    onClick: (Seat) -> Unit = {}
){
    val seats by viewModel.seats.collectAsState()

    if (seats.size < 38) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        SeatLayout(seats = seats, onClick = { onClick(it) })
    }
}