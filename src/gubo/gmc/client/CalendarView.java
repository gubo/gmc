
package gubo.gmc.client;

import java.util.*;

import com.google.gwt.dom.client.*;
import com.google.gwt.view.client.*;
import com.google.gwt.core.client.*;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.uibinder.client.*;
import com.google.gwt.user.cellview.client.*;

import gubo.gmc.shared.*;

@SuppressWarnings("nls")
public class CalendarView extends Composite implements CalendarPresenter.Display
{
	private static CalendarViewUiBinder uiBinder = GWT.create( CalendarViewUiBinder.class );
	@UiField SimplePanel tableContainer;

	interface CalendarViewUiBinder extends UiBinder<Widget,CalendarView> {}

	public interface CalendarCellTableResources extends CellTable.Resources 
	{
		@Override
		@Source( { CellTable.Style.DEFAULT_CSS, "CalendarCellTable.css" } )
		TableStyle cellTableStyle();
		 
		interface TableStyle extends CellTable.Style {}
	}
	private CellTable.Resources cellTableResources = GWT.create( CalendarCellTableResources.class );
	
	@SuppressWarnings("unused")
	private final CellTable<CalendarEventItem> table = new CellTable<CalendarEventItem>( 15,cellTableResources );

	public CalendarView() {
		initWidget( uiBinder.createAndBindUi( this ) );
		initialize();
	}

	@Override
	@SuppressWarnings("unused")
	public void showCalendarEvents( final CalendarEvents calendarEvents ) {
		if ( (calendarEvents == null) || (calendarEvents.items == null) ) { return; }
		
		final List<CalendarEventItem> items = new ArrayList<CalendarEventItem>();
		items.addAll( Arrays.asList( calendarEvents.items ) );
		
//		table.setStyleName( "font-size:4em;" );
	    table.setRowCount( items.size(),true );
	    table.setRowData( 0,items );
	    
	    tableContainer.clear();
	    tableContainer.add( table );	    
	}
	
	@SuppressWarnings("unused")
	private void initialize() {
		final TextColumn<CalendarEventItem> yearColumn = new TextColumn<CalendarEventItem>() {
			@Override
			public String getValue( final CalendarEventItem entry ) {
				return Utils.getPrettyYear( entry.start.dateTime );
			}
		};
	    table.addColumn( yearColumn );
	    
		final TextColumn<CalendarEventItem> timeColumn = new TextColumn<CalendarEventItem>() {
			@Override
			public String getValue( final CalendarEventItem entry ) {
				final String pretty = Utils.getPrettyTime( entry.start.dateTime ) + " - " + Utils.getPrettyTime( entry.end.dateTime ); 
				return pretty;
			}
		};
	    table.addColumn( timeColumn );

		final TextColumn<CalendarEventItem> summaryColumn = new TextColumn<CalendarEventItem>() {
			@Override
			public String getValue( final CalendarEventItem entry ) {
				return entry.summary;
			}
		};
	    table.addColumn( summaryColumn );

	    table.setWidth( "100%",true );
	    table.setColumnWidth( 0,10.25,Style.Unit.EM );
	    table.setColumnWidth( 1,27.25,Style.Unit.EM );
	    
	    final SingleSelectionModel<CalendarEventItem> selectionModel = new SingleSelectionModel<CalendarEventItem>();
	    table.setSelectionModel( selectionModel );
	    
	    final SelectionChangeEvent.Handler handler = new SelectionChangeEvent.Handler() {
			@Override
			public void onSelectionChange( final SelectionChangeEvent event ) {
				DBG.m( "SELECTED " + selectionModel.getSelectedObject().summary );
			}
	    };
	    selectionModel.addSelectionChangeHandler( handler );
	}
}
