package com.mock_project.ui.detailScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jicsoftwarestudio.movie_ass.R

@Composable
fun SimilarMoviesSectionView() {
    Column(
        Modifier
            .padding(15.dp),
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        Text(text = "Similar movies", color = Color.Blue)
        HorizontalDivider(
            modifier = Modifier
                .height(4.dp)
                .width(100.dp),
            thickness = 4.dp,
            color = Color.Blue
        )
        Card(
            modifier = Modifier
                .height(175.dp)
                .fillMaxWidth(),
        ) {
            Column(
            ) {
                Image(
                    painter = painterResource(id = R.drawable.mv3),
                    contentDescription = "",
                    modifier = Modifier
                        .height(101.dp)
                        .fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(5.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Column(
                        Modifier.padding(10.dp)
                    ) {
                        Text(text = "Miclrale from haeven", fontSize = 15.sp)
                        Text(text = "Drama | Fantasy | Romance", fontSize = 15.sp)
                    }
                    Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_baseline_star_24),
                            contentDescription = ""
                        )
                        Image(
                            painter = painterResource(id = R.drawable.ic_baseline_star_24),
                            contentDescription = ""
                        )
                        Image(
                            painter = painterResource(id = R.drawable.ic_baseline_star_24),
                            contentDescription = ""
                        )
                        Image(
                            painter = painterResource(id = R.drawable.ic_baseline_star_24),
                            contentDescription = ""
                        )
                    }
                }
            }
        }
        Card(
            modifier = Modifier
                .height(175.dp)
                .fillMaxWidth(),
        ) {
            Column(
            ) {
                Image(
                    painter = painterResource(id = R.drawable.mv3),
                    contentDescription = "",
                    modifier = Modifier
                        .height(101.dp)
                        .fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(5.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Column(
                        Modifier.padding(10.dp)
                    ) {
                        Text(text = "Miclrale from haeven", fontSize = 15.sp)
                        Text(text = "Drama | Fantasy | Romance", fontSize = 15.sp)
                    }
                    Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_baseline_star_24),
                            contentDescription = ""
                        )
                        Image(
                            painter = painterResource(id = R.drawable.ic_baseline_star_24),
                            contentDescription = ""
                        )
                        Image(
                            painter = painterResource(id = R.drawable.ic_baseline_star_24),
                            contentDescription = ""
                        )
                        Image(
                            painter = painterResource(id = R.drawable.ic_baseline_star_24),
                            contentDescription = ""
                        )
                    }
                }
            }
        }
        Card(
            modifier = Modifier
                .height(175.dp)
                .fillMaxWidth(),
        ) {
            Column(
            ) {
                Image(
                    painter = painterResource(id = R.drawable.mv3),
                    contentDescription = "",
                    modifier = Modifier
                        .height(101.dp)
                        .fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(5.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Column(
                        Modifier.padding(10.dp)
                    ) {
                        Text(text = "Miclrale from haeven", fontSize = 15.sp)
                        Text(text = "Drama | Fantasy | Romance", fontSize = 15.sp)
                    }
                    Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_baseline_star_24),
                            contentDescription = ""
                        )
                        Image(
                            painter = painterResource(id = R.drawable.ic_baseline_star_24),
                            contentDescription = ""
                        )
                        Image(
                            painter = painterResource(id = R.drawable.ic_baseline_star_24),
                            contentDescription = ""
                        )
                        Image(
                            painter = painterResource(id = R.drawable.ic_baseline_star_24),
                            contentDescription = ""
                        )
                    }
                }
            }
        }
    }
}