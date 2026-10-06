package com.example.mad.ui

import android.content.Intent
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Agriculture
import androidx.compose.material.icons.filled.Article
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Newspaper
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.Spa
import androidx.compose.material.icons.filled.Storefront
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mad.AdminHomepage
import com.example.mad.CropItem
import com.example.mad.DashboardViewModel
import com.example.mad.FarmLocation
import com.example.mad.LearningItem
import com.example.mad.NewsUserRead
import com.example.mad.ProductItem
import com.example.mad.WeatherState
import com.example.mad.ArticleUserRead
import com.example.mad.CropsReadUser
import com.example.mad.productreadadmin
import java.text.DateFormat
import java.util.Calendar

private enum class AppSection(val label: String) {
    Home("Home"),
    Farm("My farm"),
    Market("Market"),
    Learn("Learn")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgriApp(viewModel: DashboardViewModel) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    var section by rememberSaveable { mutableStateOf(AppSection.Home) }
    var showLocationPicker by rememberSaveable { mutableStateOf(false) }
    val context = LocalContext.current

    Scaffold(
        contentWindowInsets = WindowInsets.safeDrawing,
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = {
            NavigationBar(containerColor = MaterialTheme.colorScheme.surface) {
                AppSection.values().forEach { destination ->
                    val icon = when (destination) {
                        AppSection.Home -> Icons.Filled.Home
                        AppSection.Farm -> Icons.Filled.Agriculture
                        AppSection.Market -> Icons.Filled.Storefront
                        AppSection.Learn -> Icons.Filled.MenuBook
                    }
                    NavigationBarItem(
                        selected = section == destination,
                        onClick = { section = destination },
                        icon = { Icon(icon, contentDescription = null) },
                        label = { Text(destination.label) }
                    )
                }
            }
        }
    ) { contentPadding ->
        Crossfade(targetState = section, label = "main navigation") { page ->
            when (page) {
                AppSection.Home -> HomeScreen(
                    state = state,
                    contentPadding = contentPadding,
                    onLocationClick = { showLocationPicker = true },
                    onRefresh = { viewModel.refresh() },
                    onSectionClick = { section = it },
                    onAdminClick = { context.startActivity(Intent(context, AdminHomepage::class.java)) }
                )

                AppSection.Farm -> FarmScreen(
                    crops = state.snapshot.crops,
                    isLoading = state.isLoadingFarm,
                    error = state.farmError,
                    contentPadding = contentPadding,
                    onRetry = { viewModel.refresh() },
                    onManage = { context.startActivity(Intent(context, AdminHomepage::class.java)) }
                )

                AppSection.Market -> MarketScreen(
                    products = state.snapshot.products,
                    isLoading = state.isLoadingFarm,
                    error = state.farmError,
                    contentPadding = contentPadding,
                    onRetry = { viewModel.refresh() },
                    onManage = { context.startActivity(Intent(context, productreadadmin::class.java)) }
                )

                AppSection.Learn -> LearnScreen(
                    articles = state.snapshot.articles,
                    news = state.snapshot.news,
                    isLoading = state.isLoadingFarm,
                    error = state.farmError,
                    contentPadding = contentPadding,
                    onRetry = { viewModel.refresh() },
                    onArticlesClick = { context.startActivity(Intent(context, ArticleUserRead::class.java)) },
                    onNewsClick = { context.startActivity(Intent(context, NewsUserRead::class.java)) }
                )
            }
        }
    }

    if (showLocationPicker) {
        LocationPicker(
            selected = state.location,
            onDismiss = { showLocationPicker = false },
            onSelect = {
                viewModel.selectLocation(it)
                showLocationPicker = false
            }
        )
    }
}

@Composable
private fun HomeScreen(
    state: com.example.mad.DashboardState,
    contentPadding: PaddingValues,
    onLocationClick: () -> Unit,
    onRefresh: () -> Unit,
    onSectionClick: (AppSection) -> Unit,
    onAdminClick: () -> Unit
) {
    var menuExpanded by remember { mutableStateOf(false) }
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(
            start = 20.dp,
            top = contentPadding.calculateTopPadding() + 12.dp,
            end = 20.dp,
            bottom = contentPadding.calculateBottomPadding() + 16.dp
        ),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = greeting(),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "Grow with confidence",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
                Box {
                    IconButton(onClick = { menuExpanded = true }) {
                        Icon(Icons.Filled.MoreVert, contentDescription = "More options")
                    }
                    DropdownMenu(
                        expanded = menuExpanded,
                        onDismissRequest = { menuExpanded = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Farm management") },
                            leadingIcon = { Icon(Icons.Filled.Agriculture, null) },
                            onClick = {
                                menuExpanded = false
                                onAdminClick()
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Refresh dashboard") },
                            leadingIcon = { Icon(Icons.Filled.Refresh, null) },
                            onClick = {
                                menuExpanded = false
                                onRefresh()
                            }
                        )
                    }
                }
            }
        }

        item {
            Surface(
                onClick = onLocationClick,
                shape = RoundedCornerShape(24.dp),
                color = Color.Transparent,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .background(
                            Brush.linearGradient(
                                colors = listOf(Color(0xFF245C3A), Color(0xFF39784B))
                            )
                        )
                        .padding(20.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    Icons.Filled.LocationOn,
                                    contentDescription = null,
                                    tint = Color(0xFFC8E4BF),
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(Modifier.width(4.dp))
                                Text(
                                    text = "${state.location.name}, Sri Lanka",
                                    color = Color(0xFFE0EEDC),
                                    style = MaterialTheme.typography.labelLarge
                                )
                            }
                            Spacer(Modifier.height(8.dp))
                            Text(
                                text = "Your farm weather",
                                color = Color.White,
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        IconButton(onClick = onRefresh) {
                            Icon(
                                Icons.Filled.Refresh,
                                contentDescription = "Refresh weather and farm data",
                                tint = Color.White
                            )
                        }
                    }
                    Spacer(Modifier.height(14.dp))
                    WeatherContent(weatherState = state.weather)
                }
            }
        }

        item {
            SectionHeading(title = "Your farm at a glance", subtitle = dateToday())
        }

        item {
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                StatCard(
                    modifier = Modifier.weight(1f),
                    icon = Icons.Filled.Spa,
                    count = state.snapshot.crops.size,
                    label = "Crops"
                )
                StatCard(
                    modifier = Modifier.weight(1f),
                    icon = Icons.Filled.Storefront,
                    count = state.snapshot.products.size,
                    label = "Listings"
                )
                StatCard(
                    modifier = Modifier.weight(1f),
                    icon = Icons.Filled.Article,
                    count = state.snapshot.articles.size + state.snapshot.news.size,
                    label = "Guides"
                )
            }
            if (state.isLoadingFarm) {
                Text(
                    text = "Updating your saved farm data…",
                    modifier = Modifier.padding(top = 8.dp),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.bodySmall
                )
            } else if (state.farmError != null) {
                Text(
                    text = state.farmError,
                    modifier = Modifier.padding(top = 8.dp),
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }

        item {
            FieldTip(weatherState = state.weather)
        }

        item {
            SectionHeading(title = "What would you like to do?")
        }

        item {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    QuickLink(
                        modifier = Modifier.weight(1f),
                        title = "Explore crops",
                        subtitle = "Field records & growing",
                        icon = Icons.Filled.Spa,
                        onClick = { onSectionClick(AppSection.Farm) }
                    )
                    QuickLink(
                        modifier = Modifier.weight(1f),
                        title = "Farm market",
                        subtitle = "Supplies & listings",
                        icon = Icons.Filled.ShoppingBag,
                        onClick = { onSectionClick(AppSection.Market) }
                    )
                }
                QuickLink(
                    modifier = Modifier.fillMaxWidth(),
                    title = "Learn & stay informed",
                    subtitle = "Practical guides and agriculture news",
                    icon = Icons.Filled.MenuBook,
                    onClick = { onSectionClick(AppSection.Learn) }
                )
            }
        }
    }
}

@Composable
private fun WeatherContent(weatherState: WeatherState) {
    when (weatherState) {
        WeatherState.Loading -> Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(22.dp),
                color = Color.White,
                strokeWidth = 2.dp
            )
            Spacer(Modifier.width(12.dp))
            Text("Getting live weather…", color = Color.White)
        }

        is WeatherState.Failed -> Text(
            text = weatherState.message,
            color = Color.White,
            style = MaterialTheme.typography.bodyMedium
        )

        is WeatherState.Ready -> {
            val weather = weatherState.weather
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = weatherIcon(weather.code),
                    fontSize = 42.sp
                )
                Spacer(Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "${weather.temperature.toInt()}°",
                        color = Color.White,
                        fontSize = 40.sp,
                        fontWeight = FontWeight.Bold,
                        lineHeight = 44.sp
                    )
                    Text(
                        text = weatherDescription(weather.code),
                        color = Color(0xFFE0EEDC),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "Feels like ${weather.feelsLike.toInt()}°",
                        color = Color.White,
                        style = MaterialTheme.typography.labelLarge
                    )
                    Text(
                        text = "Updated ${DateFormat.getTimeInstance(DateFormat.SHORT).format(
                            java.util.Date.from(weatherState.updatedAt)
                        )}",
                        color = Color(0xFFE0EEDC),
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            }
            Spacer(Modifier.height(18.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0x3322392A))
                    .padding(horizontal = 14.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                WeatherMetric(Icons.Filled.WaterDrop, "${weather.humidity}%", "Humidity")
                WeatherMetric(Icons.Filled.Cloud, "${weather.precipitation} mm", "Rain")
                WeatherMetric(Icons.Filled.Agriculture, "${weather.windSpeed} km/h", "Wind")
            }
        }
    }
}

@Composable
private fun WeatherMetric(icon: ImageVector, value: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(icon, contentDescription = null, tint = Color(0xFFD8E8CE), modifier = Modifier.size(15.dp))
            Spacer(Modifier.width(4.dp))
            Text(value, color = Color.White, fontWeight = FontWeight.SemiBold, style = MaterialTheme.typography.labelLarge)
        }
        Spacer(Modifier.height(3.dp))
        Text(label, color = Color(0xFFD8E8CE), style = MaterialTheme.typography.labelSmall)
    }
}

@Composable
private fun FieldTip(weatherState: WeatherState) {
    val tip = when (weatherState) {
        is WeatherState.Ready -> when {
            weatherState.weather.precipitation >= 2.0 ->
                "Rain is expected around your area. Check field drainage and hold off on applying sprays."
            weatherState.weather.temperature >= 32.0 ->
                "Warm conditions today. Water early in the morning and check young plants for heat stress."
            weatherState.weather.code in 51..67 || weatherState.weather.code in 80..82 ->
                "Showery weather can spread plant disease. Inspect leaves and avoid working wet soil."
            else ->
                "Before watering, check soil moisture at the roots. A short field walk can prevent wasted water."
        }

        WeatherState.Loading -> "Live weather helps tailor your daily field decisions."
        is WeatherState.Failed -> "Choose a location or try again later to get weather-aware field advice."
    }
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF3DD))
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.Top
        ) {
            Text("🌱", fontSize = 23.sp)
            Spacer(Modifier.width(12.dp))
            Column {
                Text(
                    "Today's field tip",
                    color = Color(0xFF84541E),
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.height(4.dp))
                Text(tip, color = Color(0xFF55432C), style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}

@Composable
private fun StatCard(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    count: Int,
    label: String
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 14.dp, horizontal = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(20.dp))
            Spacer(Modifier.height(7.dp))
            Text(
                count.toString(),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Text(
                label,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.labelSmall,
                maxLines = 1
            )
        }
    }
}

@Composable
private fun SectionHeading(title: String, subtitle: String? = null) {
    Column {
        Text(
            title,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )
        if (subtitle != null) {
            Spacer(Modifier.height(3.dp))
            Text(
                subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun QuickLink(
    modifier: Modifier,
    title: String,
    subtitle: String,
    icon: ImageVector,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(18.dp),
        color = MaterialTheme.colorScheme.surface,
        shadowElevation = 1.dp
    ) {
        Row(
            modifier = Modifier.padding(15.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
            }
            Spacer(Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(title, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.SemiBold)
                Spacer(Modifier.height(2.dp))
                Text(
                    subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
private fun FarmScreen(
    crops: List<CropItem>,
    isLoading: Boolean,
    error: String?,
    contentPadding: PaddingValues,
    onRetry: () -> Unit,
    onManage: () -> Unit
) {
    RecordListScreen(
        title = "My farm",
        subtitle = "Your saved crop records",
        count = crops.size,
        isLoading = isLoading,
        error = error,
        contentPadding = contentPadding,
        onRetry = onRetry,
        onManage = onManage,
        manageLabel = "Open farm management",
        emptyTitle = "Your crop list is ready to grow",
        emptyMessage = "Add field records to keep your crops and growing regions in one place."
    ) {
        items(crops) { crop ->
            RecordCard(
                icon = Icons.Filled.Spa,
                title = crop.name.ifBlank { "Unnamed crop" },
                subtitle = crop.region.ifBlank { "Region not specified" },
                detail = crop.price.takeIf { it.isNotBlank() }?.let { "Market price: $it" }
            )
        }
    }
}

@Composable
private fun MarketScreen(
    products: List<ProductItem>,
    isLoading: Boolean,
    error: String?,
    contentPadding: PaddingValues,
    onRetry: () -> Unit,
    onManage: () -> Unit
) {
    RecordListScreen(
        title = "Farm market",
        subtitle = "Supplies and product listings saved in your app",
        count = products.size,
        isLoading = isLoading,
        error = error,
        contentPadding = contentPadding,
        onRetry = onRetry,
        onManage = onManage,
        manageLabel = "Manage market listings",
        emptyTitle = "No market listings yet",
        emptyMessage = "Add products from farm management to see your local listings here."
    ) {
        items(products) { product ->
            RecordCard(
                icon = Icons.Filled.ShoppingBag,
                title = product.name.ifBlank { "Unnamed product" },
                subtitle = product.region.ifBlank { "Region not specified" },
                detail = product.price.takeIf { it.isNotBlank() }?.let { "Listed at $it" }
            )
        }
    }
}

@Composable
private fun LearnScreen(
    articles: List<LearningItem>,
    news: List<LearningItem>,
    isLoading: Boolean,
    error: String?,
    contentPadding: PaddingValues,
    onRetry: () -> Unit,
    onArticlesClick: () -> Unit,
    onNewsClick: () -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(
            start = 20.dp,
            top = contentPadding.calculateTopPadding() + 20.dp,
            end = 20.dp,
            bottom = contentPadding.calculateBottomPadding() + 20.dp
        ),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            SectionHeading("Learn & grow", "Farming knowledge and updates from your local library")
        }
        if (isLoading) {
            item { LoadingMessage() }
        } else if (error != null) {
            item { ErrorMessage(error, onRetry) }
        } else {
            item {
                SectionHeading("Practical guides", "${articles.size} saved articles")
            }
            if (articles.isEmpty()) {
                item {
                    EmptyMessage(
                        title = "No guides saved yet",
                        message = "Your farming articles will appear here when they are added."
                    )
                }
            } else {
                items(articles.take(4)) { article ->
                    RecordCard(
                        icon = Icons.Filled.Article,
                        title = article.title.ifBlank { "Untitled guide" },
                        subtitle = article.date,
                        detail = article.description.takeIf { it.isNotBlank() }
                    )
                }
            }
            item {
                OutlinedButton(onClick = onArticlesClick, modifier = Modifier.fillMaxWidth()) {
                    Icon(Icons.Filled.Article, contentDescription = null)
                    Spacer(Modifier.width(8.dp))
                    Text("Browse all articles")
                }
            }
            item {
                SectionHeading("Agriculture news", "${news.size} saved updates")
            }
            if (news.isEmpty()) {
                item {
                    EmptyMessage(
                        title = "No news updates yet",
                        message = "New agriculture news will show up here when it is published."
                    )
                }
            } else {
                items(news.take(4)) { item ->
                    RecordCard(
                        icon = Icons.Filled.Newspaper,
                        title = item.title.ifBlank { "Untitled update" },
                        subtitle = item.date,
                        detail = item.description.takeIf { it.isNotBlank() }
                    )
                }
            }
            item {
                OutlinedButton(onClick = onNewsClick, modifier = Modifier.fillMaxWidth()) {
                    Icon(Icons.Filled.Newspaper, contentDescription = null)
                    Spacer(Modifier.width(8.dp))
                    Text("Browse all news")
                }
            }
        }
    }
}

@Composable
private fun RecordListScreen(
    title: String,
    subtitle: String,
    count: Int,
    isLoading: Boolean,
    error: String?,
    contentPadding: PaddingValues,
    onRetry: () -> Unit,
    onManage: () -> Unit,
    manageLabel: String,
    emptyTitle: String,
    emptyMessage: String,
    records: androidx.compose.foundation.lazy.LazyListScope.() -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(
            start = 20.dp,
            top = contentPadding.calculateTopPadding() + 20.dp,
            end = 20.dp,
            bottom = contentPadding.calculateBottomPadding() + 20.dp
        ),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            SectionHeading(title, "$count records · $subtitle")
        }
        if (isLoading) {
            item { LoadingMessage() }
        } else if (error != null) {
            item { ErrorMessage(error, onRetry) }
        } else if (count == 0) {
            item { EmptyMessage(emptyTitle, emptyMessage) }
        } else {
            records()
        }
        item {
            Button(onClick = onManage, modifier = Modifier.fillMaxWidth()) {
                Icon(Icons.Filled.Agriculture, contentDescription = null)
                Spacer(Modifier.width(8.dp))
                Text(manageLabel)
            }
        }
    }
}

@Composable
private fun RecordCard(
    icon: ImageVector,
    title: String,
    subtitle: String,
    detail: String?
) {
    Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.Top
        ) {
            Icon(
                icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .padding(top = 2.dp)
                    .size(22.dp)
            )
            Spacer(Modifier.width(12.dp))
            Column {
                Text(title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
                Spacer(Modifier.height(3.dp))
                Text(subtitle, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                if (detail != null) {
                    Spacer(Modifier.height(8.dp))
                    Text(
                        detail,
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}

@Composable
private fun EmptyMessage(title: String, message: String) {
    Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
    ) {
        Column(Modifier.padding(18.dp)) {
            Text(title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
            Spacer(Modifier.height(5.dp))
            Text(message, color = MaterialTheme.colorScheme.onSurfaceVariant, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
private fun LoadingMessage() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CircularProgressIndicator(modifier = Modifier.size(20.dp), strokeWidth = 2.dp)
        Spacer(Modifier.width(12.dp))
        Text("Loading your saved records…", color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}

@Composable
private fun ErrorMessage(message: String, onRetry: () -> Unit) {
    Column {
        Text(message, color = MaterialTheme.colorScheme.error)
        TextButton(onClick = onRetry) { Text("Try again") }
    }
}

@Composable
private fun LocationPicker(
    selected: FarmLocation,
    onDismiss: () -> Unit,
    onSelect: (FarmLocation) -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Choose your farm region") },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                FarmLocation.supported.forEach { location ->
                    TextButton(
                        onClick = { onSelect(location) },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Filled.LocationOn,
                                contentDescription = null,
                                tint = if (location == selected) {
                                    MaterialTheme.colorScheme.primary
                                } else {
                                    MaterialTheme.colorScheme.onSurfaceVariant
                                }
                            )
                            Spacer(Modifier.width(12.dp))
                            Text(
                                location.name,
                                modifier = Modifier.weight(1f),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            if (location == selected) {
                                Text("Selected", color = MaterialTheme.colorScheme.primary, style = MaterialTheme.typography.labelSmall)
                            }
                        }
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) { Text("Close") }
        }
    )
}

private fun greeting(): String = when (Calendar.getInstance().get(Calendar.HOUR_OF_DAY)) {
    in 5..11 -> "Good morning"
    in 12..16 -> "Good afternoon"
    else -> "Good evening"
}

private fun dateToday(): String = DateFormat.getDateInstance(DateFormat.FULL).format(Calendar.getInstance().time)

private fun weatherIcon(code: Int): String = when (code) {
    0 -> "☀️"
    1, 2, 3 -> "🌤️"
    45, 48 -> "🌫️"
    in 51..67 -> "🌦️"
    in 71..77 -> "❄️"
    in 80..82 -> "🌧️"
    in 85..86 -> "🌨️"
    in 95..99 -> "⛈️"
    else -> "🌤️"
}

private fun weatherDescription(code: Int): String = when (code) {
    0 -> "Clear sky"
    1 -> "Mainly clear"
    2 -> "Partly cloudy"
    3 -> "Overcast"
    45, 48 -> "Foggy"
    in 51..55 -> "Drizzle"
    in 56..57 -> "Freezing drizzle"
    in 61..65 -> "Rain"
    in 66..67 -> "Freezing rain"
    in 71..77 -> "Snow"
    in 80..82 -> "Rain showers"
    in 85..86 -> "Snow showers"
    in 95..99 -> "Thunderstorm"
    else -> "Current conditions"
}
