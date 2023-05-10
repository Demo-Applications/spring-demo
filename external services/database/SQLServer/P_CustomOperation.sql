CREATE PROCEDURE [dbo].[P_CustomOperation]
    @emb_field2 NUMERIC(38, 0),
    @date DATETIME2(6),
    @res1 BIGINT OUTPUT
AS
BEGIN
    SET NOCOUNT ON;
    DECLARE @res2 INT;

    BEGIN TRY
        -- calculate the value of res1
        SET @res1 = 100 + CAST(@emb_field2 AS BIGINT);

        -- calculate the value of res2 using the @date input parameter
        SET @res2 = DATEPART(YEAR, @date) + DATEPART(MONTH, @date) + DATEPART(DAY, @date);

        -- return the values of res1 and res2
        SELECT @res1 AS res1, @res2 AS res2;
    END TRY
    BEGIN CATCH
        -- log the error
        DECLARE @errorMessage NVARCHAR(4000);
        SET @errorMessage = ERROR_MESSAGE() + ' ' + ERROR_PROCEDURE() + ' line ' + CAST(ERROR_LINE() AS NVARCHAR(5));
        RAISERROR (@errorMessage, 16, 1);

        -- return a negative value for res1 and res2 to indicate error
        SET @res1 = -1;
        SET @res2 = -1;
        SELECT @res1 AS res1, @res2 AS res2;
    END CATCH;
END;
